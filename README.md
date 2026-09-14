# Server Utils

**Server Utils** is a lightweight, server-side Minecraft Forge mod for **1.7.10** that adds a handful of quality-of-life utilities for server administration: custom join/leave messages, dimension-change logging, an update checker, and a couple of admin/utility commands.

- **Mod ID:** `serverutils`
- **Author:** xabyxd
- **Minecraft version:** 1.7.10
- **Forge dependency:** `Forge@[10.13.4.1614]` or newer
- **Side:** Server-only (clients do not need to install it — `acceptableRemoteVersions = "*"`)

## Features

### Custom join / leave messages
Replaces the vanilla "X joined the game" / "X left the game" messages with a more visual broadcast:

- `» **PlayerName** has joined the server!` (green)
- `« **PlayerName** has left the server!` (red)

The original vanilla messages are intercepted at the network layer (via a Netty channel handler) so they never reach clients, avoiding duplicate join/leave messages.

### Welcome message on login
When a player joins, they receive a short personalized welcome message framed by separators:

- If the player is an **OP**, they get an admin-flavored welcome message.
- Regular players get a standard welcome message.

Both messages are configurable (see below).

### Dimension change logging
Logs every dimension change to the server console, including both the origin and destination dimension names. This can be toggled via config (see below).

### Update checker
On startup, the mod asynchronously checks a remote `version.txt` against the running mod version and logs whether an update is available. Both the check itself and the remote URL are configurable.

### Commands

| Command | Usage | Permission | Description |
|---|---|---|---|
| `/greet` | `/greet <player\|everyone>` | OP | Sends a green "Hello" chat message to a specific player, or to everyone on the server if `everyone` is used. Supports tab-completion of online player names. |
| `/getlocation` | `/getlocation <player>` | OP | Prints the target player's UUID, exact coordinates, and current dimension (id + name) to the command sender. Supports tab-completion of online player names. |
| `/info` | `/info` | Everyone | Prints a configurable list of informational lines (server info, credits, etc.) to chat. |
| `/sureload` | `/sureload` | OP | Reloads `serverutils.cfg` from disk without restarting the server. |

More to come... in [*TODO.md*](TODO.md).

All commands are server commands, registered on `FMLServerStartingEvent`.

## Configuration

On first launch, the mod generates its own config folder (instead of a single flat file in `config/`):

```
config/serverutils/serverutils.cfg
```

| Option | Category | Type | Default | Description |
|---|---|---|---|---|
| `REMOTE_VERSION_URL` | `general` | String | `https://xabyserver.ddns.net/ServerUtils/version.txt` | URL checked by the update checker. |
| `logDimensionChanges` | `general` | boolean | `true` | Whether dimension changes should be logged to the server console. |
| `enableVersionChecker` | `version checker` | boolean | `true` | Enable or disable the update checker. |
| `normalUserWelcomeMessage` | `welcome messages` | String | `Welcome to the server!` | Welcome message sent to regular players on join. |
| `opUserWelcomeMessage` | `welcome messages` | String | `Welcome back,` | Welcome message sent to OPs on join. |
| `commandInfo` | `commands` | String list | server info / credits lines | Lines printed by `/info`, one entry per line. |

Running `/sureload` re-reads this file and applies changes without needing to restart the server.

## Installation

1. Download the mod `.jar`.
2. Drop it into your server's `mods` folder.
3. Make sure Forge `10.13.4.1614` or newer is installed for Minecraft 1.7.10.
4. Start the server — the `config/serverutils/` folder and `serverutils.cfg` will be generated automatically.

Since this is a server-side mod, players do **not** need to install anything on their client to join a server running it.

## Building from source

This project uses a Gradle build for Forge 1.7.10 (ForgeGradle-style setup), with the mod version stamped in automatically from `version.txt` (falling back to `git describe` when unavailable) and injected into the `@VERSION@` placeholder in `Serverutils.java`.

```bash
./gradlew build
```

On Windows, use `gradlew.bat`. A Java 8 JDK (e.g. `1.8.0_202`) is required to build against this Forge/Minecraft version.

The built jar will be output under `build/libs/`.

## Project structure

```
src/main/java/net/xabyxd/ServerUtils/
├── Serverutils.java              # @Mod entry point, FML lifecycle events
├── CommonProxy.java               # Wires up config, events and commands
├── config/
│   └── Config.java                # Forge Configuration handling
├── commands/
│   ├── CommandGreet.java
│   ├── CommandGetLocation.java
│   ├── CommandInfo.java
│   └── CommandReload.java
├── events/
│   ├── PlayerJoinHandler.java             # Join/leave broadcasts, welcome message, dimension logging
│   └── VanillaJoinMessageFilter.java      # Suppresses vanilla join/leave chat packets
└── utils/
    ├── VersionChecker.java        # Async update check against a remote version.txt
    └── LogHelper.java             # Logger wrapper around log4j
    └── TimeFormat.java            # Formats time in ticks
```

## License

Apache 2.0 License — see [*LICENSE*](LICENSE) for details.
