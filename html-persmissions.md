### When working with nginx, if i want to update a site with a non-root user, what is the default way to allow a non-root user tu edit files in the html directory

On a typical Linux system with **nginx**, the *default and recommended* way to let a **non-root user update site files** (e.g. under `/var/www/html`) is **not** to give them sudo/root access, but to manage **ownership and permissions** properly.

Below is the standard approach used on most servers.

---

## 1. Understand the defaults

* Nginx worker processes usually run as:

  ```
  user www-data;   # Debian/Ubuntu
  user nginx;      # RHEL/CentOS/Alma/Rocky
  ```
* Default web root:

  ```
  /var/www/html
  ```
* By default:

  * Owned by `root:root`
  * Writable only by root

---

## 2. Recommended default solution: **Shared group ownership**

### Step 1: Create or reuse a web group

Usually the nginx user’s group already exists.

Examples:

```bash
getent group www-data   # Debian/Ubuntu
getent group nginx      # RHEL-based
```

If you want a dedicated group:

```bash
sudo groupadd web
```

---

### Step 2: Add your non-root user to that group

```bash
sudo usermod -aG www-data youruser
# or
sudo usermod -aG web youruser
```

⚠️ Log out and back in for group changes to apply.

---

### Step 3: Change ownership of the web directory

```bash
sudo chown -R root:www-data /var/www/html
# or
sudo chown -R root:web /var/www/html
```

* Root keeps control
* Group can edit files

---

### Step 4: Set directory and file permissions

```bash
sudo find /var/www/html -type d -exec chmod 2755 {} \;
sudo find /var/www/html -type f -exec chmod 664 {} \;
```

What this does:

* `2` (setgid) → new files inherit the group
* Group can write
* World cannot write (important for security)

---

## 3. Resulting permission model

| Role             | Access       |
| ---------------- | ------------ |
| root             | Full control |
| nginx (www-data) | Read files   |
| non-root user    | Edit files   |
| others           | Read-only    |

This is the **default, sane production setup**.

---

## 4. Alternative (less common) approaches

### A. ACLs (Advanced, flexible)

Used when multiple users need different access levels:

```bash
sudo setfacl -R -m u:youruser:rwX /var/www/html
sudo setfacl -R -d -m u:youruser:rwX /var/www/html
```

Useful, but **not the default** for simple setups.

---

### B. Direct ownership (NOT recommended)

```bash
sudo chown -R youruser:youruser /var/www/html
```

❌ Breaks separation between system and application
❌ Risky in multi-user environments

---

## 5. What you should **not** do

* ❌ `chmod 777 /var/www/html`
* ❌ Run nginx as your user
* ❌ Give sudo just to edit HTML files

---

## TL;DR (Default Answer)

> **The default way is to use group ownership:**
> Keep `/var/www/html` owned by root, give write access to a shared group (e.g. `www-data`), and add the non-root user to that group.

If you want, tell me:

* Your distro (Ubuntu, Debian, RHEL, etc.)
* Whether this is production or dev

and I’ll tailor the commands exactly to your setup.

### what if i just do chmod o+w to the html dir? It's just me using the server.

Short answer: **it will work, but it’s a bad idea even if you’re the only user**.

Here’s why, in practical terms.

---

## What `chmod o+w /var/www/html` actually does

It makes the directory **world-writable**:

```
drwxrwxrwx  /var/www/html
         ↑
      anyone
```

That means **any process on the system** can:

* Create files
* Modify files
* Delete files
  inside your web root.

Not just human users — *every running service*.

---

## Why this is dangerous *even on a single-user server*

### 1. Compromised services = instant site takeover

If **any** service running as a different user is compromised (cron job, CI agent, database exploit, image processing bug, etc.), an attacker can:

* Drop PHP shells
* Inject JS malware
* Replace your site content

No privilege escalation needed — you already gave write access.

---

### 2. Web apps amplify the risk

If nginx serves:

* PHP (php-fpm)
* Node apps
* Upload endpoints
* CMS (WordPress, etc.)

A single file-upload bug becomes **full write access** to your site.

---

### 3. Breaks defense-in-depth

Linux permissions are your **last line of defense**.
`o+w` removes that line entirely.

Security people treat world-writable web roots as a **critical misconfiguration**.

---

### 4. Bad habits migrate to production

Most security incidents start with:

> “It was just a small personal server…”

Configs get copied. Memory sticks. Cloud images reused.

---

## When is `o+w` *acceptable*?

Very narrow cases only:

* Disposable local VM
* Short-lived dev container
* Teaching/demo environment
* No public network exposure

Even then, it’s still discouraged.

---

## Better “I’m alone, keep it simple” alternatives

### Option 1: Group-based (2 commands, safe)

```bash
sudo chown -R root:www-data /var/www/html
sudo chmod -R g+w /var/www/html
sudo chmod g+s /var/www/html
sudo usermod -aG www-data youruser
```

Log out/in. Done.

---

### Option 2: ACL (one-liner, very clean)

```bash
sudo setfacl -R -m u:youruser:rwX /var/www/html
sudo setfacl -R -d -m u:youruser:rwX /var/www/html
```

No chmod games, no world-write.

---

### Option 3: Dev convenience (bind mount or symlink)

Edit files in your home directory, serve via:

```bash
ln -s /home/youruser/site /var/www/html
```

Permissions stay sane.

---

## Rule of thumb

> **If nginx can read it, attackers will try to write it.**
> Don’t help them by making it world-writable.

---

### TL;DR

* ✅ Yes, `chmod o+w` works
* ❌ No, it’s not safe — even solo
* ✅ Use group ownership or ACLs instead

If you want, tell me:

* Is this bare metal, VM, or Docker?
* Static files only, or PHP/Node?

I’ll give you the *simplest safe setup* for your case.
