# Chat App RMI Java 💬

[![GitHub Repository](https://img.shields.io/badge/GitHub-Repository-blue?logo=github)](https://github.com/LKanan/chat_app_rmi_java.git)
[![Java 17+](https://img.shields.io/badge/Java-17%2B-orange)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Une application de chat en temps réel pour deux utilisateurs utilisant le protocole **RMI (Remote Method Invocation)** en Java. Cette application permet à deux utilisateurs sur des machines différentes du même réseau de communiquer en envoyant et recevant des messages.

---

## 📋 Table des matières

- [Vue d'ensemble](#vue-densemble)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Dépendances](#dépendances)
- [Troubleshooting](#troubleshooting)

---

## 🎯 Vue d'ensemble

### 📚 Contexte académique

Ce projet est un **TP (Travaux Pratiques)** du cours **Applications Réparties** proposant une implémentation de chat bidirectionnelle utilisant le protocole **RMI (Remote Method Invocation)** en Java.

**Objectifs pédagogiques** :
- 🔌 Comprendre les principes de la communication RMI
- 🌐 Implémenter une application client-serveur distribuée
- 💬 Gérer la communication asynchrone entre processus distants
- 📦 Maîtriser la sérialisation d'objets Java
- 🔐 Implémenter l'authentification dans une application répartie

### Architecture de l'application

Cette application est composée de **deux parties** :

1. **Server** : Expose les services RMI et gère l'authentification des utilisateurs
2. **Client** : Application cliente pour deux utilisateurs (User1 et User2) qui peuvent se connecter et échanger des messages

### Caractéristiques principales :

✅ Communication RMI bidirectionnelle entre deux utilisateurs  
✅ Authentification par email et mot de passe  
✅ Enregistrement des messages en JSON  
✅ Interface CLI simple et intuitive  
✅ Gestion des sessions utilisateur  
✅ Architecture multi-thread avec threads serveur et client  

---

## 🏗️ Architecture

### Modèle Client-Serveur RMI

```
┌─────────────────────────────────────────────────────────────┐
│                      User 1 (Client)                         │
│                  IP: 10.87.233.155:1099                     │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  UserService (RMI Server)                            │   │
│  │  - Interface: IUser1Remote                           │   │
│  │  - Méthodes: seConnecter(), receiveMessage()        │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           ↕ RMI
┌─────────────────────────────────────────────────────────────┐
│                 Server (Machine Tier)                       │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  UserService                                         │   │
│  │  ├── UserMetier (Métier)                            │   │
│  │  │   └── UserDao (Données)                          │   │
│  │  ├── JsonLogger (Traçage)                           │   │
│  │  └── Interfaces Distantes                           │   │
│  │      └── IUser1Remote, IUser2Remote                 │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           ↕ RMI
┌─────────────────────────────────────────────────────────────┐
│                      User 2 (Client)                         │
│                  IP: 10.87.233.57:1099                      │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  UserService (RMI Server)                            │   │
│  │  - Interface: IUser2Remote                           │   │
│  │  - Méthodes: receiveMessage(), saveMessage()        │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 Prérequis

- **Java 17+** (JDK)
- **Maven 3.6+** pour la gestion des dépendances
- **Connexion réseau** entre les deux machines
- **Port RMI 1099** accessible
- **GSON** (bibliothèque pour sérialisation JSON)

### Vérifier l'installation de Java :

```bash
java -version
```

---

## 🚀 Installation

### 1. Cloner le dépôt (ou télécharger les fichiers)

```bash
git clone https://github.com/LKanan/chat_app_rmi_java.git
cd chat_app_rmi_java
```

### 2. Compiler le Server

```bash
cd Server
mvn clean install
```

### 3. Compiler le Client

```bash
cd ../Client
mvn clean install
```

---

## ⚙️ Configuration

### Étape 1 : Modifier les adresses IP

Vous **DEVEZ** configurer les adresses IP réelles de vos machines.

#### Pour le Server (User1) - `Server/src/Main.java`

Remplacez `10.87.233.155` par l'adresse IP réelle de la machine Server :

```java
System.setProperty("java.rmi.server.hostname", "10.87.233.155");
```

#### Pour le Client (User2) - `Client/src/main/java/Main.java`

Remplacez les adresses IP :
- Votre IP locale (User2) : `10.87.233.57`
- IP du Server (User1) : `10.87.233.155`

```java
System.setProperty("java.rmi.server.hostname", "10.87.233.57");
IUser1Remote stubUser1 = (IUser1Remote) Naming.lookup("rmi://10.87.233.155:1099/user1");
```

### Étape 2 : Vérifier la connectivité réseau

Assurez-vous que les deux machines peuvent ping l'une l'autre :

```bash
ping 10.87.233.155
ping 10.87.233.57
```

---

## 📖 Utilisation

### Démarrer le Server (User1)

```bash
cd Server
mvn exec:java -Dexec.mainClass="Main"
```

**Résultat attendu :**
```
Serveur user1 démarré
Connection du client...1
Connecté à User1 !
```

### Démarrer le Client (User2)

Dans une autre terminal, après avoir démarré le Server :

```bash
cd Client
mvn exec:java -Dexec.mainClass="Main"
```

**Résultat attendu :**
```
Serveur User2 démarré localement.
Connexion au serveur de l'ami (User1)...
Connecté à User1 !
Veuillez entrer vos identifiants
===============================
Email : 
```

### Flux de conversation

1. **User2 se connecte** avec email et mot de passe
2. **User2 envoie un message** (Ex: "Bonjour")
   - Le message est reçu par User1
   - Enregistré en JSON
3. **User1 répond** avec un message
   - Le message est envoyé à User2
   - Enregistré en JSON
4. Entrez `quitter` pour terminer la connexion

### Exemple de session :

```
User1 > Saisir un message (ou 'quitter' pour sortir) :
<= : Bonjour User2 !
=> User2 : Comment ça va ?
<= : Très bien merci !
=> User2 : quitter
```

---

## 📁 Structure du projet

### Server

```
Server/
├── src/
│   ├── Main.java                    # Point d'entrée du serveur
│   ├── dao/
│   │   └── UserDao.java            # Accès aux données utilisateurs
│   ├── metier/
│   │   └── UserMetier.java         # Logique métier (authentification)
│   ├── model/
│   │   └── User.java               # Modèle utilisateur (Serializable)
│   ├── service/
│   │   ├── UserService.java        # Implémentation des services RMI
│   │   ├── IUser1Remote.java       # Interface distante User1
│   │   └── IUser2Remote.java       # Interface distante User2
│   └── util/
│       └── JsonLogger.java         # Enregistrement des messages en JSON
├── pom.xml                          # Configuration Maven
├── messages.json                    # Historique des messages
└── target/                          # Fichiers compilés
```

### Client

```
Client/
├── src/main/java/
│   ├── Main.java                    # Point d'entrée du client
│   ├── model/
│   │   └── User.java               # Modèle utilisateur
│   └── service/
│       ├── UserService.java        # Implémentation locale des services
│       ├── IUser1Remote.java       # Interface distante User1
│       └── IUser2Remote.java       # Interface distante User2
├── pom.xml                          # Configuration Maven
├── chat_log.txt                     # Historique local des chats
└── target/                          # Fichiers compilés
```

---

## 🎓 Concepts clés apprises

### Remote Method Invocation (RMI)
- Appels de méthodes sur des objets distants comme locaux
- Utilisation du registre RMI pour la découverte de services
- Liaison (`rebind`) et recherche (`lookup`) de services

### Architecture distribuée
- Modèle client-serveur en Java
- Communication inter-processus via le réseau
- Gestion de multiples connexions simultanées

### Programmation multi-thread
- Threads côté serveur pour réception des messages
- Threads côté client pour envoi des messages
- Synchronisation des opérations asynchrones

### Sérialisation d'objets
- Implémentation de `Serializable` pour les échanges RMI
- Transmission d'objets complexes sur le réseau
- Handling des exceptions de sérialisation

### Gestion des données et authentification
- Pattern DAO (Data Access Object)
- Pattern Métier (Business Logic)
- Validation des identifiants utilisateur

### Logging et traçabilité
- Logging JSON des messages
- Historique des communications
- Monitoring des connexions

---

## 🔗 Dépendances

### GSON (JSON Processing)

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

Cette dépendance est utilisée pour sérialiser/désérialiser les messages en JSON via `JsonLogger`.

---

## 🔀 Flux de communication RMI

### 1. **Enregistrement RMI** (Démarrage)

```
User1            Server         Registre RMI
 │                 │                 │
 ├─ createRegistry(1099)         creates port
 │                 │                 │
 ├─ rebind("rmi://localhost:1099/user1", skeleton)
 │                 ├─────────────────→│
 │                 │           register service
 │                 │←─────────────────┤
 └─ Service enregistré
```

### 2. **Recherche de service distant** (Connexion)

```
User2            Server         Registre RMI
 │                 │                 │
 ├─ lookup("rmi://10.87.233.155:1099/user1")
 │                                    │
 │←────────────── stub des arguments
```

### 3. **Échange de messages**

```
User1                                        User2
  │                                            │
  │  ← receiveMessage("Bonjour") via RMI ─────┤
  │                                            │
  ├─ saveMessage("user2", "Salut")            │
  │                                            │
  ├─ receiveMessage("Salut") via RMI ────────→│
  │                                            │
  └─ quitter ──────────────────────────────────┘
```

---

## 📝 Format des logs JSON

Les messages sont enregistrés dans `Server/messages.json` et `Client/chat_log.txt` :

```json
{
  "user": "user1",
  "message": "Bonjour !",
  "timestamp": "2026-03-03T10:30:45"
}
{
  "user": "user2",
  "message": "Salut, comment ça va ?",
  "timestamp": "2026-03-03T10:30:50"
}
```

---

## 🐛 Troubleshooting

### ❌ Erreur : `Connection refused`

**Cause** : La connexion au serveur RMI est refusée.

**Solution** :
1. Vérifiez que le Server est démarré
2. Vérifiez les adresses IP configurées
3. Vérifiez que le port 1099 n'est pas bloqué par le firewall

### ❌ Erreur : `NotBoundException`

**Cause** : Le service RMI n'est pas enregistré.

**Solution** :
1. Attendez quelques secondes après le démarrage du Server
2. Vérifiez que le nom du service correspond : `rmi://localhost:1099/user1`

### ❌ Erreur : `RemoteException: Authentication failed`

**Cause** : Les identifiants sont incorrects.

**Solution** :
- Vérifiez email et mot de passe dans `UserDao.java`
- Les identifiants par défaut sont généralement : `user1@example.com` / `password1`

### ❌ Les messages n'apparaissent pas

**Cause** : Les threads ne sont pas synchronisés correctement.

**Solution** :
1. Le Server utilise deux threads (server + client)
2. Assurez-vous que les deux applications tournent en parallèle
3. Vérifiez les logs dans `messages.json`

### ❌ Erreur de sérialisation

**Cause** : Les objets ne sont pas `Serializable`.

**Solution** :
- La classe `User` doit implémenter `Serializable`
- Tous les objets transmis via RMI doivent être sérialisables

---

## � Améliorations futures

### Court terme
- [ ] Interface graphique (Swing ou JavaFX)
- [ ] Support de plusieurs utilisateurs simultanés
- [ ] Chiffrement des messages (SSL/TLS)
- [ ] Chat de groupe (N-to-N)
- [ ] Base de données persistante

### Moyen terme
- [ ] Protocole WebSocket comme alternative à RMI
- [ ] Notification en temps réel
- [ ] Historique complet des conversations
- [ ] Recherche de messages
- [ ] Gestion des utilisateurs hors-ligne

### Avancé
- [ ] Migration vers gRPC ou Netty
- [ ] Déploiement cloud (Docker, Kubernetes)
- [ ] Intégration avec middleware (ActiveMQ, RabbitMQ)
- [ ] Haute disponibilité et load-balancing

---

## �🔐 Sécurité

⚠️ **Attention** : Cette application est à titre éducatif. Pour la production :

- ✅ Utilisez une authentification chiffrée (SSL/TLS)
- ✅ Chiffrez les messages
- ✅ Validez toutes les entrées
- ✅ Utilisez un système de logs sécurisé
- ✅ Limitez l'accès au port RMI

---

## 📚 Références

- [Documentation Java RMI](https://docs.oracle.com/javase/tutorial/rmi/)
- [GSON GitHub](https://github.com/google/gson)
- [Maven Official Documentation](https://maven.apache.org/guides/index.html)

---

## 👨‍💻 Auteurs

| Étudiant | GitHub | Rôle |
|----------|--------|------|
| **L. Kanan** | [@lkanan](https://github.com/rooneyi) | Développeur principal - Server & RMI Services |
| **Rooney I** | [@rooneyi](https://github.com/lkanan) | Développeur principal - Client & Communication |

### 📦 Repository officiel

🔗 **GitHub** : [https://github.com/LKanan/chat_app_rmi_java.git](https://github.com/LKanan/chat_app_rmi_java.git)

**Cloner le projet** :
```bash
git clone https://github.com/LKanan/chat_app_rmi_java.git
cd chat_app_rmi_java
```

Projet réalisé en **Mars 2026** dans le cadre du cours **Applications Réparties**

---

## 📄 Licence

Voir le fichier `Server/LICENSE` pour plus de détails.
                                                                                                                                                                                                                                                                                                                                                      