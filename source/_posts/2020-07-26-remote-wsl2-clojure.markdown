---
layout: post
title: "Configuring a remote Clojure dev machine with Windows 10 and WSL2"
date: 2020-07-26 18:15:08 +0100
comments: true
categories: [wsl2, clojure]
---

In the last few years I've been developing predominantly on Apple
MacBooks. Recently I built myself a
[new](https://tweakers.net/pricewatch/bestelkosten/2370528)
[PC](https://twitter.com/borkdude/status/1280852700612177922) with a fast
processor and lots of memory. I wanted to move some of my development tasks to
that machine. I still like the freedom that laptops give me to work from
wherever I want. Be it from the couch in my own home or from a random coffee
place in the city where I live, I just like to change environment every few
hours and not sit behind my desk all day long. I hadn't used Windows as my
primary OS for almost 10 years. But since Windows 10 (still) comes with RDP,
which is better than VNC in terms of performance, and with WSL2, which offers a
"real" and well integrated linux experience, I was curious if I could create a
setup for remotely working on the new machine. If it didn't work out, I could
always go dual boot with Ubuntu as the primary dev OS and use VNC if I needed to
do anything graphical. For what it is worth, here is a description of my current
setup.

Because I had no other Windows machines in my home, I tried creating a USB
Windows installation drive in macOS based on
[this](https://www.freecodecamp.org/news/how-make-a-windows-10-usb-using-your-mac-build-a-bootable-iso-from-your-macs-terminal/)
blog post. No matter what I tried, at the end of the installation process
Windows [would complain](https://twitter.com/borkdude/status/1280943718363734016) with

> Windows installation encountered an unexpected error. Verify that the
> installation sources are accessible, and restart the installation.

A friend offered help and created an installation drive using
[Rufus](https://rufus.ie/) on his Windows Home installation, which did work.

Once Windows was installed, I [enabled RDP](https://docs.microsoft.com/en-us/windows-server/remote/remote-desktop-services/clients/remote-desktop-allow-access) and installed
[WSL2](https://docs.microsoft.com/en-us/windows/wsl/install-win10), [Ubuntu 20.04](https://www.microsoft.com/store/apps/9n6svws3rx71), [Windows Terminal](https://www.microsoft.com/en-us/p/windows-terminal/9n0dx20hk701?activetab=pivot:overviewtab)
and [Docker Desktop for WSL2](https://docs.docker.com/docker-for-windows/wsl/).

I intend to use Ubuntu WSL2 as my primary dev environment on this machine, so it
made sense to tweak the [Terminal configuration](https://superuser.com/questions/1456511/is-there-a-way-to-change-the-default-shell-in-windows-terminal)
to open Ubuntu by default, instead of PowerShell. I found it annoying that
Ubuntu in Terminal starts in the Windows home directory (`/mnt/c/Users/borkdude`), so I set `"startingDirectory":
"//wsl$/Ubuntu-20.04/home/borkdude"` in my Ubuntu profile.

I use zsh as my default shell on macOS and wanted that in WSL2 as well so I
installed that using `sudo apt update && sudo apt install zsh` followed by `chsh -s $(which
zsh)`. I also installed [ohmyzsh](https://github.com/ohmyzsh/ohmyzsh) and enabled the
only two plugins I use on a daily basis: `git` and `jump`.

Then I started porting the dev setup I use for work on my MacBook, mainly
consisting of Docker images, a couple of Clojure projects, knitted together with
some bash scripts. I use a couple of shell scripts to set all my development
environment variables and aliases for easily setting up ssh
tunnels. These worked after only making a few tweaks (the ethernet card is
called differently in macOS and Ubuntu in WSL2). Of course I needed to install
`git`. To run Clojure projects I installed OpenJDK version 8 and 11 via `apt`. I
use [jenv](jenv.be) to manage Java versions on a project basis, which worked
perfectly:

``` sh
sudo apt-get install openjdk-8-jdk
jenv add /usr/lib/jvm/java-1.8.0-openjdk-amd64/
```

Also I installed [boot](https://boot-clj.com/), a Clojure build tool and the
[Clojure CLI](https://clojure.org/guides/deps_and_cli) which are all well
supported in linux.

Before running my work stuff, I wanted to copy over a PostgreSQL database and
other data from my MacBook using `rsync`. Before I could do that, I needed to
enable `ssh` in Ubuntu. I found out I could do this by running `sudo service ssh
start` but I still could not log in from my MacBook. It turns out you also have
to forward and open up the port in the Windows Firewall. I found
[this](https://github.com/microsoft/WSL/issues/4150#issuecomment-504209723)
PowerShell script that lets you do that. As the comment suggests, I also created
a scheduled task that runs this script upon Windows login. I did the same thing
for running the `ssh` service using `wsl -u root sudo service ssh start`. Be
sure to select `Run using the highest privileges`. I'm sure there is a better
way to do this via `systemd` in WSL2, but this works for me. So now I could
`rsync` my the data from macOS to WSL2. I started the Docker containers, the
Clojure projects and it worked! The I/O performance of Docker in WSL2 seems
great so far.

Enabling SSH in WSL also lets me use Emacs from my laptop and edit files on the
remote machine using [tramp mode](https://www.emacswiki.org/emacs/TrampMode). I
ran into one issue with this. Since I use a non-standard prompt in zsh I added
this hack to my `~/.zshrc`:

``` sh
case "$TERM" in
    "dumb")
        bash
esac
```

Emacs sets `TERM` to `"dumb"` when opening files using `tramp`. When this is the
case, I just invoke `bash` instead of continuing with `zsh`. There may be a
better solution, but for now it works.


Of course I also wanted to be able to run Emacs on the machine itself. I first
tried to run Emacs in Windows [natively](https://github.com/m-parashar/emax64),
but I read somewhere that editing WSL2 files directly from Windows is not
recommended. Also you might run into the line ending differences between Windows
and linux/macOS. I decided to play it safe and install emacs inside WSL2 using
`apt`. If you do decide to use the Windows-native Emacs and have trouble finding
where emacs expects configuration files, you can find this using `M-x
describe-variable user-init-file`. On my machine that was
`C:\Users\borkdude\AppData\Roaming\.config\emacs\init.el.`. To get a graphical
UI for the emacs started from WSL2, I needed to install an X-server on
Windows. There are plenty of free and open source solutions to choose from, but
I chose to buy
[X410](https://www.microsoft.com/en-us/p/x410/9nlp712zmn9q?activetab=pivot:overviewtab)
on sale for $9.99 instead of $49.99. It seems to be actively worked on and has
[good documentation](https://x410.dev/cookbook/wsl/using-x410-with-wsl2/). To
automatically start X410 on Windows startup I followed
[these](https://x410.dev/cookbook/automatically-start-x410-on-login/)
instructions. When started, in the tray there is an X icon where you can modify
settings for X410. I have enabled Windowed Apps, Allow Public Access, DPI
Scaling (High Quality) and Shared Clipboard. To export the right `DISPLAY` value
for GUI applications in WSL2 I have this in my `.zshenv`:

``` sh
export DISPLAY=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2; exit;}'):0.0
```

and then run emacs using `setsid emacs` from a zsh session (I created an alias
for this in my `.zshenv`).

I'm using the exact same emacs config I use on macOS, which is based on
[@bbatsov](https://twitter.com/bbatsov)'s
[prelude](https://github.com/bbatsov/prelude). It looks and feels the same as on
macOS, although now I probably have to learn the "real" emacs keybindings for
copying (M-x) and pasting (C-y) instead of using the macOS keybindings, which
may be in fact a nice side effect of this experiment.

Even `cider-connect` works seemlessly from my laptop, provided that I forward
and open up the port in Windows Firewall like described above.

Some closing tips.

There is a known [issue](https://github.com/microsoft/WSL/issues/4166) with
memory usage of WSL2. Since linux uses non-allocated memory for filesystem
caching, Windows thinks this memory is really used. As time goes by, Windows
could end up allocating all your system's memory to WSL2. To put a limit to
this, I use this config in `C:\Users\borkdude\.wslconfig`:

```
[wsl2]

memory=92GB
```

My PC has a whopping 128GB of memory so this still leaves 36GB of memory to
Windows.

As I will mainly use this PC for work, I turn it off at night and during the
weekends. To get back where I left, without restarting all my development
processes from scratch, I use the
[Hibernate](https://support.microsoft.com/en-us/help/920730/how-to-disable-and-re-enable-hibernation-on-a-computer-that-is-running)
setting.

I use [Tailscale](https://tailscale.com/) to set up a VPN between my laptop and
Windows machine so I can connect from my laptop to the PC via Remote desktop,
emacs tramp and/or nREPL when I go outside of my home or hack from the couch.

As a bonus I can re-use some licenses I already owned for apps I am using on
macOS: Beyond Compare and Acronis True Image.

I'm pleasantly surprised with WSL2, the Terminal app and Docker
integration. I've only been using this setup for week but so far so good.
