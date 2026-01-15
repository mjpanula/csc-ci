On a modern **Ubuntu VPS (20.04, 22.04, 24.04)** the **best practice** is to install **Docker Engine + Docker Compose v2 (plugin)** from Docker’s official repository, not the old `docker-compose` Python binary.

This approach is:

* officially supported by Docker
* up to date
* secure
* works cleanly with port 8080 (or any port)

Below is a **clean, production-ready setup**.

---

## 1. Update the system

```bash
sudo apt update && sudo apt upgrade -y
```

---

## 2. Install prerequisites

```bash
sudo apt install -y ca-certificates curl gnupg lsb-release
```

---

## 3. Add Docker’s official GPG key

```bash
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
  | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

---

## 4. Add Docker repository

```bash
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" \
  | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

Then update:

```bash
sudo apt update
```

---

## 5. Install Docker Engine + Docker Compose plugin

```bash
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

---

## 6. Enable Docker at boot

```bash
sudo systemctl enable docker
sudo systemctl start docker
```

---

## 7. (Recommended) Run Docker without sudo

```bash
sudo usermod -aG docker $USER
```

Then **log out and back in**.

Test:

```bash
docker run hello-world
```

---

## 8. Verify Docker Compose v2

```bash
docker compose version
```

You should see something like:

```
Docker Compose version v2.xx.x
```

---

## 9. Hosting services on port 8080 (example)

Docker does **not** block ports by default. You only need:

* correct port mapping
* firewall open (if enabled)

### Example `docker-compose.yml`

```yaml
services:
  web:
    image: nginx:alpine
    ports:
      - "8080:80"
```

Start it:

```bash
docker compose up -d
```

Test:

```bash
curl http://your-vps-ip:8080
```

---

## 10. Firewall check (important on VPS)

If UFW is enabled:

```bash
sudo ufw status
```

Allow port 8080:

```bash
sudo ufw allow 8080/tcp
sudo ufw reload
```

---

## Common mistakes to avoid ❌

* Installing `docker-compose` via `apt` (old v1, deprecated)
* Using random install scripts
* Forgetting firewall rules
* Binding services to `127.0.0.1` instead of `0.0.0.0`

---

## Optional: Production tips

If this VPS is for real workloads:

* Put **Traefik or Nginx Proxy Manager** in front
* Use **Docker volumes** for persistence
* Enable **automatic updates** (e.g. Watchtower)
* Use **non-root containers** when possible

I can tailor the setup exactly for your use case.
