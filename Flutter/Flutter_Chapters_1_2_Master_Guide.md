# Flutter Master Guide: Chapters 1 & 2

> Written as a teacher's study guide, based on your `cours1.pdf` and `cours2.pdf`.
> Goal: **understand** every idea, then **master** it by practicing.

**How to use this guide**
1. Read each section and run every code example (DartPad or your own project).
2. Do the "Try it yourself" exercises. Do not skip them.
3. Finish with the quiz and the mini-project at the end.

---

# Table of Contents

**Chapter 1: Why Flutter? Setup and Project Structure**
1. Mobile development approaches (Native / Hybrid / Compile-to-native)
2. React Native vs Flutter
3. Market trends and performance
4. The Google ecosystem
5. Installation step by step
6. Creating your first project
7. Anatomy of a Flutter project
8. DartPad (online playground)

**Chapter 2: Widgets**
1. What is a widget?
2. StatelessWidget
3. `child` vs `children`
4. First complete example
5. Example 2: separate class (Hello Rectangle)
6. Building a custom widget (Unit Converter row)
7. StatefulWidget and `setState`
8. AppBar
9. TabBar
10. Floating AppBar (SliverAppBar)
11. Column and Row
12. Flexible
13. Expanded
14. Container
15. Image
16. Material Buttons

**Final:** Cheat sheet, common errors, quiz, mini-project

---

# CHAPTER 1: WHY FLUTTER?

## 1.1 Three ways to build a mobile app

### A) Native apps
You write **one separate app per platform**, using that platform's official tools.

| | Android Native | iOS Native |
|---|---|---|
| Languages | Java, Kotlin | Objective-C, Swift |
| SDK / tools | Android SDK, Gradle, XML layouts | Xcode, CocoaPods |
| Editor | Android Studio | Xcode |

- Pros: best performance, full access to every device feature.
- Cons: two codebases, two teams (or one team learning two stacks), double the cost and time.

### B) Hybrid technologies
Examples: **PhoneGap, Cordova, Ionic**. You write **HTML + CSS + JavaScript**, and the app runs inside a **WebView** (a mini-browser embedded in the app).

- Pros: easy for web developers.
- Cons: can be **slow**, because everything runs in a browser. The code **cannot be shared** with a normal web app (according to the course).

### C) Compile to native
Examples: **NativeScript, React Native, Xamarin, Flutter**. One codebase is compiled or bridged into a real mobile app that you can publish to the stores.

- Pros: real apps in the stores, run fast.
- Cons: learning the framework may be difficult.

### Bonus: Progressive Web Apps (PWA)
- Built with HTML/CSS and React, Angular or Vue.
- **Not a real app**: it runs in a web browser and is not in the app stores.
- Cannot access many device resources (accelerometer, compass, proximity, sensors, Bluetooth, NFC).
- Easy to write.

**Memory trick:** *Native = best but double work. Hybrid = easy but slow. Compile-to-native = the sweet spot.*

---

## 1.2 React Native vs Flutter

| Criterion | React Native | Flutter |
|---|---|---|
| Language | JavaScript + React | **Dart** |
| Age / maturity | Older (10+ years), most problems already solved | Newer (~3 years at the time of the course), now stable and mature |
| Community | Very large (many libraries, but compatibility issues) | Growing fast, backed by Google |
| Widgets | Few built-in widgets; you depend on external libraries (version problems, optimization) | **Large set of pre-built, Google-optimized widgets**, so apps are fast and quick to develop |
| Look on iOS vs Android | Not always the same; often needs different projects | **Exactly the same design** on iOS, Android and web in one project ("pixel-perfect") |
| Cross-platform | Not truly cross-platform | Mobile, web, desktop |

**What is Dart?** A programming language optimized for multi-platform apps, developed by Google. It is used for mobile, desktop, server and web.

**Why "pixel-perfect"?** Flutter does not use the phone's native buttons. It **draws every pixel itself** with its own rendering engine, so a button looks identical everywhere.

---

## 1.3 Trends and job market (from the course)

| Technology | Before 2019 | 2019-2021 | Forecast 2023 (incl. PWA) |
|---|---|---|---|
| React Native + React | 42% | 39% | 33% |
| Flutter + Dart | 26% | 37% | **48%** |
| Native and others (Unity, Ionic, Cordova) | 32% | 20% | 19% |

**Reading the table:** React Native was dominant, Flutter grew quickly and was forecast to overtake it.

## 1.4 Performance (from the course)

| Technology | React Native | Flutter | Native |
|---|---|---|---|
| Execution time (relative) | 100% | 25% | 5% |
| Memory (relative) | 100% | 20% | 4% |

The slide showed a demo of the same ToDo app: React Native took about 4.14 s, Flutter about 1.23 s, Native about 0.26 s.

> **Teacher's note:** these numbers come from one specific benchmark of the time. Real performance depends on the app, the version, and how you code. Treat them as "native is fastest, Flutter is close behind, and in that test Flutter was ahead of React Native", not as universal truth. Always benchmark your own app for serious decisions.

## 1.5 One language for the whole Google ecosystem

With **Dart + Flutter** you can target:
- Chrome (Progressive Web App)
- Android mobile apps
- Hosting on Firebase
- Machine learning with Google Cloud

"All Google technologies using one language."

---

## 1.6 Installation (Windows, as in the course)

Official site: **flutter.dev**

### Step 0: System requirements
- Windows 7 SP1 or later (64-bit). *Today, use Windows 10/11 64-bit.*
- Disk space: about 400 MB (not including IDE/tools).
- Tools: **Windows PowerShell 5.0+** (pre-installed on Windows 10) and **Git for Windows 2.x** with the option **"Use Git from the Windows Command Prompt"**.

Check that Git works: open Command Prompt and type `git --version`.

### Step 1: Install Git
Download "Git for Windows" (64-bit setup) and install it.

### Step 2: Get the Flutter SDK
Two ways:
1. **Download the zip** (for example `flutter_windows_...-stable.zip`), then extract it into a folder like `C:\src\flutter`.
   - Do **not** install it in `C:\Program Files\` because that location needs elevated (admin) privileges.
2. **Clone with Git** (always the latest, and you can switch branches):
   ```bash
   git clone https://github.com/flutter/flutter.git
   ```

The folder contains `bin`, `packages`, `examples`, `dev`, `flutter_console.bat`, etc.

### Step 3: Add Flutter to your PATH
So you can type `flutter` from any terminal:
1. Open **System Properties > Advanced > Environment Variables**.
2. Under **User variables** (or System variables), select **Path** > **Edit** > **New**.
3. Add the full path to the `bin` folder, for example `C:\src\flutter\bin`.
4. Click OK, then **close and reopen** your terminal.

Other variables you may see in the course screenshots: `ANDROID_HOME` (path to the Android SDK), `JAVA_HOME`, `DART_SDK`.

### Step 4: Run `flutter doctor`
```bash
flutter doctor
```
This command **checks your environment** and prints a report. Items marked `[✓]` are fine; items marked `[✗]` or `[!]` (shown in bold with instructions) need action.

Typical problem from the course:
```
[-] Android toolchain - develop for Android devices
    ✗ Android SDK is missing command line tools; download from ...
```

### Step 5: Install Android Studio
It provides the **Android SDK**, the **emulator**, and platform tools.
- Download from developer.android.com/studio and install it.
- If `flutter doctor` complains about **cmdline-tools**:
  1. Open **SDK Manager** (Configure > SDK Manager).
  2. Go to **SDK Tools** and **uncheck "Hide Obsolete Packages"**.
  3. Install **Android SDK Command-line Tools** (in the course: "Android SDK Tools (Obsolete)").
  4. Apply and let Studio download it.
  5. Run `flutter doctor` again.

### Step 6: JDK (only if required)
If `flutter doctor` says Java/JDK is missing, install a JDK from Oracle (or OpenJDK). Android Studio often bundles its own JDK, so you may not need this.

### Step 7: Accept all licenses
```bash
flutter doctor --android-licenses
```
Type `y` for each prompt. The course summary: **"Run flutter doctor and accept all licences!"**

### Goal: a clean report
```
[✓] Flutter (Channel stable ...)
[✓] Android toolchain - develop for Android devices
[✓] Android Studio
[✓] VS Code
[✓] Connected device (1 available)
• No issues found!
```

### Step 8: Set up the Android emulator (AVD)
1. **Enable VM acceleration** on your machine (virtualization in BIOS, Intel HAXM / Hyper-V / WHPX).
2. Android Studio > **Tools > AVD Manager** (or Configure > AVD Manager on the welcome screen) > **Create Virtual Device**.
3. Choose a **device definition** (for example Pixel 2) > **Next**.
4. Choose a **system image** (Android version). An **x86 or x86_64** image is recommended. > Next.
5. Under *Emulated Performance*, choose **Hardware - GLES 2.0** to enable hardware acceleration.
6. Verify the configuration > **Finish**.
7. In AVD Manager, click **Run** (the play button). The emulator starts.

### Step 9: Add the Flutter and Dart plugins to Android Studio
**Configure > Plugins > search "flutter" > Install** (this also installs the Dart plugin). Restart the IDE. Now you will see **"Start a new Flutter project"** on the welcome screen.

### Step 10: (Alternative) Visual Studio Code
You may prefer VS Code:
1. Install VS Code.
2. Open **Extensions** (left bar icon), search **"Flutter"** (by Dart Code), and click Install. (It also installs Dart.)

---

## 1.7 Creating your first project

### In VS Code
1. `Ctrl + Shift + P` to open the **Command Palette**.
2. Type `Flutter: New Project` and choose **Application**.
3. Choose a folder, then type a project name (for example `hello_world`). Names must be **lowercase with underscores**.
4. Open `lib/main.dart`.
5. Choose a device (bottom right) and press **F5** to run (debug mode).

### In Android Studio
**Start a new Flutter project > Flutter Application**, fill the name and location, then press the green **Run** button.

### From the terminal (works everywhere)
```bash
flutter create hello_world
cd hello_world
flutter run
```

### The default demo app
It shows a counter and a floating "+" button: *"You have pushed the button this many times: 8"*. This is a **StatefulWidget** (you will understand it fully in Chapter 2).

### Hot reload vs hot restart
- **Hot reload** (`r` in terminal, or the lightning icon, or save the file): injects your code changes **instantly** and **keeps the app state**.
- **Hot restart** (`R`): restarts the app and **resets the state**.
- **Full stop/run**: needed after changing native files, pubspec dependencies, or `main()` in some cases.

Hot reload is Flutter's superpower. Use it constantly.

---

## 1.8 Anatomy of a Flutter project

```
my_counter_app/
├── android/          Compiled Android app (you rarely touch it)
├── ios/              iOS app (you rarely touch it)
├── lib/              ★ Where you will spend ~99% of your time
│   └── main.dart     Entry point: MUST exist and MUST contain main()
├── test/             Where you SHOULD spend ~50% of your time
│   └── widget_test.dart   (but most people spend ~0.1%)
├── .gitignore
├── pubspec.yaml      Required in all Dart projects: dependencies + metadata
├── pubspec.lock      Auto-generated lock file: do NOT edit
└── README.md
```

| File/Folder | Purpose |
|---|---|
| `lib/main.dart` | Entry point. Dart starts by running `main()`. |
| `pubspec.yaml` | Declares packages (dependencies), assets (images, fonts), SDK version, project name. |
| `pubspec.lock` | Records the exact versions resolved, so versions stay compatible. Updated automatically when `pubspec.yaml` changes. |
| `android/`, `ios/` | Platform-specific wrappers (permissions, app icon, build config). |
| `test/` | Unit and widget tests. |

**Adding a package:** edit `pubspec.yaml`:
```yaml
dependencies:
  flutter:
    sdk: flutter
  cached_network_image: ^3.3.0   # example package
```
then run `flutter pub get` (VS Code does it when you save).

**Adding an asset (image):**
```yaml
flutter:
  assets:
    - assets/loading.gif
```
**Be careful with YAML indentation**: it uses spaces (2 per level), never tabs.

---

## 1.9 DartPad: try Flutter with no installation

Go to **dartpad.dev** (online). Paste code and click **RUN**:

```dart
import 'package:flutter/material.dart';

void main() {
  runApp(
    MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('Hello Dartpad')),
        body: Center(child: Text('Hello!')),
      ),
    ),
  );
}
```

This is the fastest way to test the examples in this guide.

---

## Chapter 1: Key takeaways
- Three families: **native**, **hybrid (WebView)**, **compile-to-native**. Flutter is compile-to-native.
- Flutter uses **Dart**, **its own widgets**, and renders **pixel-perfect on every platform**, from a single codebase.
- Setup order: **Git > Flutter SDK > PATH > Android Studio > `flutter doctor` > licenses > emulator > IDE plugin > create project**.
- You work in `lib/main.dart`; dependencies live in `pubspec.yaml`.
- Use **hot reload** to iterate quickly.

### Try it yourself (Chapter 1)
1. Run `flutter doctor -v` and fix every red item.
2. Create a project named `my_first_app` and run it on the emulator.
3. Change the `title` of the demo app and observe hot reload.
4. Open `pubspec.yaml` and find the `name`, `dependencies` and `flutter:` sections.

---
---

# CHAPTER 2: WIDGETS

Reference: https://flutter.dev/docs/development/ui/widgets

## 2.1 What is a widget?

> **"Everything is a widget."** Widgets are the foundation of Flutter apps.

A widget is a **description of a part of the user interface**: a button, a text, an image, a padding, a layout, even the whole app. Widgets are **nested** inside each other, forming a **widget tree**.

Examples from the course: `Container`, `Row`, `Column`, `Icon`, `IconButton`, `Offstage`, `Stack`, `Expanded`, `Text`, `Image`, `ListView`, `Padding`, `TextStyle`, `RaisedButton` (now `ElevatedButton`).

### Screen structure
```
Screen Container Widget
 ├── Header Widget        (AppBar)
 └── Screen Layout Widget (body)
```

### Two families of widgets
```
Widget
 ├── StatelessWidget   (immutable: Text, Icon, AssetImage ...)
 ├── StatefulWidget    (has changing state: Scrollable, Animatable, Checkbox ...)
 └── ...
```

| | StatelessWidget | StatefulWidget |
|---|---|---|
| Data changes over time? | No | Yes |
| Classes needed | 1 | 2 (widget + State) |
| Redraw trigger | Parent rebuilds it | `setState()` |
| Example | Text, Icon, a static screen | Checkbox, counter, form, animation |

---

## 2.2 StatelessWidget

> An **immutable** widget. All its fields are `final` after creation.

You pass properties when you create it (example: a Container with background color, height, width) and they never change by themselves.

```dart
var container = Container(
  color: Colors.purple,
  width: 300.0,
  height: 400.0,
  margin: EdgeInsets.all(16.0),
  child: Text('Hello!'),
);
```

**Result:** a purple rectangle with "Hello!" in the top-left corner.

**Template for your own stateless widget:**
```dart
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return /* one widget (which may contain many) */;
  }
}
```

- `build()` **describes the UI**. Flutter calls it whenever the widget needs to be drawn.
- `BuildContext context` = "where this widget sits in the tree". It is used to find ancestors (Theme, Navigator, Scaffold, MediaQuery...).

---

## 2.3 `child` vs `children`

- A widget with **one** child uses `child:`. Examples: Container, Center, Padding.
- A widget with **many** children uses `children:` (a **list**). Examples: Column, Row, Stack, ListView.

```dart
Container(
  color: Colors.purple,
  width: 300.0,
  height: 400.0,
  margin: EdgeInsets.all(16.0),
  child: Column(
    children: <Widget>[
      Text('Hello!'),
      Text('Hello!'),
      Text('Hello!'),
      Text('Hello!'),
    ],
  ),
);
```

Result: four "Hello!" lines stacked vertically at the top of the rectangle.

> **Rule:** `child` takes **one widget**. `children` takes a **`List<Widget>`** (`<Widget>[ ... ]`).

---

## 2.4 First complete example

```dart
import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    home: Scaffold(
      appBar: AppBar(
        title: Text('First App'),
      ),
      body: Center(
        child: Text(
          'Hello flutter Developers',
          style: TextStyle(fontSize: 20),
        ),
      ),
    ),
  ));
}
```

**Line by line:**

| Code | Meaning |
|---|---|
| `import 'package:flutter/material.dart';` | Imports Material Design widgets. |
| `void main()` | Entry point of every Dart program. |
| `runApp(...)` | Inflates the given widget and attaches it to the screen. |
| `MaterialApp` | The root widget of a Material app (theme, routes, title). |
| `home:` | The first screen. |
| `Scaffold` | Basic page structure: `appBar`, `body`, `floatingActionButton`, `drawer`, `bottomNavigationBar`... |
| `appBar: AppBar(...)` | The **header widget** (top bar). |
| `body: Center(...)` | The **screen layout widget**. `Center` centers its child. |
| `Text(..., style: TextStyle(...))` | Text with a style. |

Tree:
```
MaterialApp
 └── Scaffold
      ├── AppBar → Text('First App')
      └── Center → Text('Hello flutter Developers')
```

---

## 2.5 Example 2: a separate class ("Hello Rectangle")

Good practice: **extract your UI into your own widget classes** instead of one huge `main()`.

```dart
import 'package:flutter/material.dart';

void main() {
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: false,   // hides the red DEBUG banner
      title: 'Hello Rectangle',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Hello Rectangle'),
        ),
        body: const HelloRectangle(),
      ),
    ),
  );
}

class HelloRectangle extends StatelessWidget {
  const HelloRectangle({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        color: Colors.greenAccent,
        height: 400.0,
        width: 300.0,
        child: const Center(
          child: Text(
            'Hello!',
            style: TextStyle(fontSize: 40.0),
            textAlign: TextAlign.center,
          ),
        ),
      ),
    );
  }
}
```

Original source: github.com/flutter/udacity-course (course/01_hello_rectangle).

**New things to notice:**
- `const`: tells Dart this widget never changes, so Flutter can **reuse it and skip rebuilds** (better performance). Use `const` whenever possible.
- `Key? key` and `super(key: key)`: keys help Flutter identify widgets. In modern Dart you write `const HelloRectangle({super.key});`.
- The text is centered **twice**: the outer `Center` places the rectangle in the screen; the inner `Center` places the text inside the rectangle.

### Running on a device (Android Studio)
Pick the device dropdown (`<no devices>`) > "Open Android Emulator: Pixel 2 XL" > press the green play button.

---

## 2.6 Building a custom widget: the "Unit Converter" row

The course asks: *"Customizable widget: how do we build this custom row?"* The built-in `ListTile` is too limited, so we build our own by **composing small widgets** (the Flutter way of thinking).

### Step-by-step composition (from the slides)
```
1. Container
2. Container → Row
3. Container → Row → [ Icon , Text ]
4. Add spacing:   Row → [ Padding(Icon) , Text ]
5. Center the text: Row → [ Padding(Icon) , Center(Text) ]
6. Make it tappable with ripple effect: wrap in InkWell
7. InkWell needs a Material ancestor to draw ripples: wrap in Material
```

Final tree:
```
Material
 └── Container
      └── InkWell
           └── Row
                ├── Padding → Icon
                └── Center  → Text
```

### Code
```dart
class Category extends StatelessWidget {
  final String name;
  final IconData iconLocation;
  final Color color;

  const Category({
    super.key,
    required this.name,
    required this.iconLocation,
    required this.color,
  });

  static const double _rowHeight = 100.0;
  static final BorderRadius _borderRadius = BorderRadius.circular(_rowHeight / 2);

  @override
  Widget build(BuildContext context) {
    // Throws an error in debug mode if there is no Material ancestor
    assert(debugCheckHasMaterial(context));

    return Material(
      color: Colors.transparent,
      child: Container(
        height: _rowHeight,
        child: InkWell(
          borderRadius: _borderRadius,
          highlightColor: color.withOpacity(0.2),
          splashColor: color.withOpacity(0.4),
          onTap: () {
            print('I was tapped!');
          },
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.only(right: 16.0),
                  child: Icon(iconLocation, size: 60.0),
                ),
                Center(
                  child: Text(
                    name,
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.headlineSmall,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
```

Use it:
```dart
Category(name: 'Length', iconLocation: Icons.straighten, color: Colors.teal)
```

**Concepts to remember:**
- `Padding` adds space around its child. `EdgeInsets.all(16)`, `.only(right: 16)`, `.symmetric(horizontal: 8)`.
- `InkWell` adds **touch response** (ripple) and `onTap`.
- `Material` is a "piece of paper" on which ink effects are drawn.
- `Theme.of(context).textTheme...` gets styles from the app theme. (In the old slides, `display1` was used; in modern Flutter use `headlineSmall`, `titleLarge`, `bodyMedium`...)
- Two ways to write functions: `() => ...` (one expression) or `() { ... }` (block).
- `crossAxisAlignment: CrossAxisAlignment.stretch` makes children fill the cross axis (here, the height).

> This "**small widgets nested together**" pattern is the heart of Flutter.

---

## 2.7 StatefulWidget and `setState`

### Why do we need state?
A **StatelessWidget** has no memory: if something changes (user taps), the screen will **not** update. A **StatefulWidget** keeps data in a separate **State** object, and `setState()` tells Flutter: *"my data changed, rebuild the UI"*.

### Structure comparison
```dart
// Stateless: one class
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return OneOrMoreWidgets;
  }
}

// Stateful: two classes
class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }
}

class MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return OneOrMoreWidgets;
  }
}
```

- The **widget** class is immutable and just creates the State.
- The **State** class holds **variables** and the `build()` method.

### Experiment 1: a Stateless widget with a checkbox
```dart
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(),
        body: Checkbox(
          value: false,
          onChanged: null,   // null = disabled checkbox
        ),
      ),
    );
  }
}
```
The checkbox is shown but **disabled** (`onChanged: null`) and always unchecked.

### Experiment 2: add a counter in the Stateless widget (the problem)
```dart
class MyApp extends StatelessWidget {
  int counter = 0;                       // variable inside a stateless widget

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(),
        body: Column(
          children: <Widget>[
            Text("Counter Value => $counter"),
            Checkbox(
              value: false,
              onChanged: (bool? newValue) {
                counter++;
                print("Counter value => $counter");
              },
            ),
          ],
        ),
      ),
    );
  }
}
```
**What happens:** the **console** prints 1, 2, 3, 4... but the **screen** always shows `0`. The variable changes, but nobody tells Flutter to **rebuild** the UI. This is exactly why Stateful widgets exist.

> Also note: Flutter will warn that fields in a StatelessWidget should be `final`.

### Solution: convert to a StatefulWidget
```dart
class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppImpl();
  }
}

class MyAppImpl extends State<MyApp> {
  int counter = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(),
        body: Column(
          children: <Widget>[
            Text("Text Value => $counter"),
            Checkbox(
              value: false,
              onChanged: (bool? newValue) {
                setState(() {
                  counter++;
                });
                print("Counter value => $counter");
              },
            ),
          ],
        ),
      ),
    );
  }
}
```
Now the text on screen updates every time you tap.

### `setState` explained
```dart
setState(() {
  counter++;      // 1. change the data
});               // 2. Flutter schedules a rebuild → build() runs again
```
- Change data **inside** `setState`.
- Only call it in a `State` class.
- It is fine to call it often, but avoid heavy work inside it.

### A complete, correct checkbox (the checkbox itself also needs state!)
In the slide, the checkbox has `value: false` forever, so it never *looks* checked. A real version:

```dart
class MyApp extends StatefulWidget {
  const MyApp({super.key});
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int counter = 0;
  bool checked = false;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Stateful demo')),
        body: Column(
          children: [
            Text('Counter value => $counter'),
            Checkbox(
              value: checked,
              onChanged: (bool? newValue) {
                setState(() {
                  checked = newValue ?? false;
                  counter++;
                });
              },
            ),
          ],
        ),
      ),
    );
  }
}
```

### State life cycle (bonus, important for mastery)
| Method | When |
|---|---|
| `createState()` | The widget is created. |
| `initState()` | Once, when the State is first inserted. Initialize things here. |
| `build()` | Every time UI must be drawn (initially and after each `setState`). |
| `didUpdateWidget()` | The parent gave new configuration. |
| `dispose()` | The State is removed. Close controllers, streams here. |

**Decision rule:** *"Will anything on this screen change while the user is using it?"* If yes, Stateful (or a state-management solution). If no, Stateless.

---

## 2.8 AppBar

The **top bar** of a screen, placed in `Scaffold(appBar: ...)`. Parts: `leading` (left), `title`, `actions` (right icons), `bottom` (e.g., TabBar).

Source: https://api.flutter.dev/flutter/material/AppBar-class.html

```dart
import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  static const String _title = 'Flutter Code Sample';

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: _title,
      home: MyStatelessWidget(),
    );
  }
}

class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('AppBar Demo'),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.add_alert),
            tooltip: 'Show Snackbar',
            onPressed: () {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('This is a snackbar')),
              );
            },
          ),
          IconButton(
            icon: const Icon(Icons.navigate_next),
            tooltip: 'Go to the next page',
            onPressed: () {
              Navigator.push(context, MaterialPageRoute<void>(
                builder: (BuildContext context) {
                  return Scaffold(
                    appBar: AppBar(
                      title: const Text('Next page'),
                    ),
                    body: const Center(
                      child: Text(
                        'This is the next page',
                        style: TextStyle(fontSize: 24),
                      ),
                    ),
                  );
                },
              ));
            },
          ),
        ],
      ),
      body: const Center(
        child: Text(
          'This is the home page',
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}
```

**New ideas:**
- `actions:` is a list of widgets on the right side (usually `IconButton`).
- `IconButton` = `icon` + `tooltip` (text on long press) + `onPressed`.
- **SnackBar**: a short message at the bottom, shown with `ScaffoldMessenger.of(context).showSnackBar(...)`.
- **Navigation:** `Navigator.push(context, MaterialPageRoute(builder: ...))` opens a new page. The new page's AppBar **automatically gets a back arrow**. To go back in code: `Navigator.pop(context)`.

---

## 2.9 TabBar

Tabs let the user switch between views. You need three things that **must have the same length**: a **controller**, a **TabBar** (the tab headers), and a **TabBarView** (the content).

Source: https://api.flutter.dev/flutter/material/TabBar-class.html

```dart
class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      initialIndex: 1,       // starts on the 2nd tab (index starts at 0)
      length: 3,             // number of tabs
      child: Scaffold(
        appBar: AppBar(
          title: const Text('TabBar Widget'),
          bottom: const TabBar(
            tabs: <Widget>[
              Tab(icon: Icon(Icons.cloud_outlined)),
              Tab(icon: Icon(Icons.beach_access_sharp)),
              Tab(icon: Icon(Icons.brightness_5_sharp)),
            ],
          ),
        ),
        body: const TabBarView(
          children: <Widget>[
            Center(child: Text("It's cloudy here")),
            Center(child: Text("It's rainy here")),
            Center(child: Text("It's sunny here")),
          ],
        ),
      ),
    );
  }
}
```

**Rule:** `length` = number of `Tab`s = number of `TabBarView` children. If they don't match, you get an error.

---

## 2.9b Floating AppBar (SliverAppBar)

**Problem:** a tall app bar takes lots of vertical space. **Solution:** hide it when the user scrolls down and reveal it as soon as they scroll back up.

Source: https://flutter.dev/docs/cookbook/lists/floating-app-bar

```dart
import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const title = 'Floating App Bar';

    return MaterialApp(
      title: title,
      home: Scaffold(
        // No appBar on the Scaffold: only a body with a CustomScrollView
        body: CustomScrollView(
          slivers: [
            // Add the app bar to the CustomScrollView
            const SliverAppBar(
              title: Text(title),
              floating: true,                 // reappears on scroll up
              flexibleSpace: Placeholder(),   // shows the shrinking size
              expandedHeight: 200,            // taller than normal
            ),
            // Then a list
            SliverList(
              delegate: SliverChildBuilderDelegate(
                (context, index) => ListTile(title: Text('Item #$index')),
                childCount: 1000,             // builds 1000 ListTiles
              ),
            ),
          ],
        ),
      ),
    );
  }
}
```

**Key vocabulary:**
- **Sliver** = a scrollable "piece" inside a `CustomScrollView`.
- `SliverAppBar` = an AppBar that reacts to scrolling. `floating: true` makes it reappear on any upward scroll.
- `SliverList` + `SliverChildBuilderDelegate` = a **lazy list**: items are built **only when they scroll into view**, so 1000 items stay fast.
- `'Item #$index'` is **string interpolation**: `$variable` inserts the value. Use `${expression}` for expressions.

---

## 2.10 Column and Row

These are the **two most important layout widgets**.

- **Column** arranges children **vertically** (main axis = vertical).
- **Row** arranges children **horizontally** (main axis = horizontal).

### Alignment properties
```dart
Column(
  mainAxisAlignment: MainAxisAlignment.center,   // along the main axis
  crossAxisAlignment: CrossAxisAlignment.start,  // across the main axis
  mainAxisSize: MainAxisSize.min,                // shrink-wrap vs fill (max)
  children: [ ... ],
)
```

| Property | Column | Row |
|---|---|---|
| `mainAxisAlignment` | vertical position (start, center, end, spaceBetween, spaceAround, spaceEvenly) | horizontal position |
| `crossAxisAlignment` | horizontal position (start, center, end, stretch) | vertical position |
| `mainAxisSize` | `min` = only as tall as children, `max` = fill all height | same, for width |

### Example (from the course, with original text replaced by a neutral one)
```dart
Column(
  crossAxisAlignment: CrossAxisAlignment.start,
  mainAxisSize: MainAxisSize.min,
  children: <Widget>[
    const Text('First line'),
    const Text('Second line'),
    const Text('Third line'),
    Text(
      'Big title!',
      style: DefaultTextStyle.of(context).style.apply(fontSizeFactor: 2.0),
    ),
  ],
)
```

### Row with Expanded children
```dart
Row(
  children: const <Widget>[
    Expanded(
      child: Text('Deliver features faster', textAlign: TextAlign.center),
    ),
    Expanded(
      child: Text('Craft beautiful UIs', textAlign: TextAlign.center),
    ),
    Expanded(
      child: FittedBox(
        fit: BoxFit.contain,       // otherwise the logo will be tiny
        child: FlutterLogo(),
      ),
    ),
  ],
)
```
`Expanded` splits the available width equally between the 3 children.

### The famous overflow error ("RIGHT OVERFLOWED BY 1022 PIXELS")
```dart
Row(
  children: const <Widget>[
    FlutterLogo(),
    Text("A very very long sentence that does not fit on one line ..."),
    Icon(Icons.sentiment_very_satisfied),
  ],
)
```
A Row **does not wrap or shrink** its children. If they are too wide, you see the **yellow-black striped warning**.

**Fix:** wrap the long child in `Expanded`:
```dart
Row(
  children: const <Widget>[
    FlutterLogo(),
    Expanded(
      child: Text("A very very long sentence that now wraps onto several lines ..."),
    ),
    Icon(Icons.sentiment_very_satisfied),
  ],
)
```
The text now takes the remaining space and **wraps**.

### Reversing direction: `textDirection`
```dart
Row(
  textDirection: TextDirection.rtl,   // right-to-left order
  children: [ FlutterLogo(), Expanded(child: Text('...')), Icon(Icons.sentiment_very_satisfied) ],
)
```
The children appear in reverse order (logo on the right, smiley on the left). Useful for **Arabic/Hebrew** layouts.

---

## 2.11 Flexible widgets

`Flexible` lets a child of a Row/Column take **a share of the available space** instead of its natural size. Source: api.flutter.dev/flutter/widgets/Flexible-class.html

### Step 1: no Flexible
```dart
Column(children: [
  Container(color: Colors.cyan),
  Container(color: Colors.teal),
  Container(color: Colors.indigo),
]);
```
Empty Containers with no child collapse to size 0, so nothing is visible.

### Step 2: wrap each child in Flexible
```dart
Column(children: [
  Flexible(child: Container(color: Colors.cyan)),
  Flexible(child: Container(color: Colors.teal)),
  Flexible(child: Container(color: Colors.indigo)),
]);
```
Space is shared **equally** (flex defaults to 1 each = 1/3 each).

### Step 3: use `flex` for proportions
```dart
Column(children: [
  Flexible(flex: 2, child: Container(color: Colors.cyan)),
  Flexible(flex: 3, child: Container(color: Colors.teal)),
  Flexible(flex: 1, child: Container(color: Colors.indigo)),
]);
```
Total flex = 2 + 3 + 1 = **6**.

| Color | flex | Share |
|---|---|---|
| cyan | 2 | 2/6 = **1/3** |
| teal | 3 | 3/6 = **1/2** |
| indigo | 1 | 1/6 |

This is how you build **responsive** layouts that adapt to any screen size and orientation.

---

## 2.12 Expanded

`Expanded` = a `Flexible` with `fit: FlexFit.tight`. It **forces** the child to fill all the remaining space along the main axis.

| | Flexible | Expanded |
|---|---|---|
| Child may be smaller than its share | Yes (`FlexFit.loose`, the default) | No, it must fill (`FlexFit.tight`) |
| Typical use | Child that should not exceed a share | Child that should fill the leftover space |

```dart
Row(
  children: [
    MyWidget(),                          // natural size
    Expanded(child: MyWidget()),         // takes ALL remaining width
    MyWidget(),                          // natural size
  ],
)
```

**With priority (flex):**
```dart
Row(
  children: [
    MyWidget(),
    Expanded(flex: 2, child: Container()),   // gets 2 parts
    MyWidget(),
    Expanded(flex: 1, child: Container()),   // gets 1 part
  ],
)
```
The remaining space is divided **2 : 1**.

> **Golden rule:** `Expanded` and `Flexible` can **only** be direct children of `Row`, `Column` or `Flex`. Otherwise you get a "Incorrect use of ParentDataWidget" error.

---

## 2.13 Container

`Container` is the **"Swiss-army knife"**: it combines sizing, color, padding, margin, alignment, decoration and transforms. Source: api.flutter.dev/flutter/widgets/Container-class.html

| Property | Effect |
|---|---|
| `child` | The widget inside |
| `color` | Background color |
| `width`, `height` | Fixed size |
| `padding` | Space **inside** the container, around the child |
| `margin` | Space **outside** the container |
| `alignment` | Position of the child inside (e.g., `Alignment.center`) |
| `decoration` | Border, radius, gradient, shadow, shape (`BoxDecoration`) |
| `transform` | Rotate, scale, translate (e.g., `Matrix4.rotationZ(0.05)`) |

### Progressive examples
```dart
// 1. "Boring": wraps its child tightly, no styling
Container(child: Text('Boring'));

// 2. Add a background color
Container(child: Text('Boring'), color: Colors.blue);

// 3. Padding (inside) + margin (outside)
Container(
  child: Text('Less boring'),
  color: Colors.blue,
  padding: EdgeInsets.all(20.0),
  margin: EdgeInsets.all(20.0),
);

// 4. A circle with BoxDecoration
Container(
  child: Text('Less boring'),
  decoration: BoxDecoration(
    shape: BoxShape.circle,
    color: Colors.blue,
  ),
  margin: EdgeInsets.all(25.0),
  padding: EdgeInsets.all(40.0),
);

// 5. Alignment: with no size, the container fills the parent, then centers the child
Container(
  child: Text('Less boring'),
  color: Colors.blue,
  alignment: Alignment.center,
);

// 6. Fixed size + alignment
Container(
  child: Text('Less boring'),
  color: Colors.blue,
  alignment: Alignment.center,
  width: 200,
  height: 100,
);

// 7. Rotation
Container(
  child: Text('Less boring'),
  color: Colors.blue,
  width: 200.0,
  height: 200.0,
  transform: Matrix4.rotationZ(0.05),
);
```

> **Important gotcha:** you **cannot use both `color` and `decoration`** on the same Container (it throws an error). If you use a `BoxDecoration`, put the color **inside** it: `decoration: BoxDecoration(color: Colors.blue)`.

> **Padding vs Margin:** *padding is the inside cushion, margin is the outside space.* Picture a framed picture: the gap between photo and frame is padding; the gap between frame and wall is margin.

---

## 2.14 Image

### Network image
```dart
Image.network('https://picsum.photos/250?image=9');
```

### Asset (local) image
1. Put the file in `assets/` (e.g., `assets/loading.gif`).
2. Declare it in `pubspec.yaml`:
   ```yaml
   flutter:
     assets:
       - assets/loading.gif
   ```
3. Use it:
   ```dart
   Image.asset('assets/loading.gif');
   ```

### Fade in an image while it loads
Source: flutter.dev/docs/cookbook/images/fading-in-images

```dart
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const title = 'Fade in images';

    return MaterialApp(
      title: title,
      home: Scaffold(
        appBar: AppBar(
          title: const Text(title),
        ),
        body: Center(
          child: FadeInImage.assetNetwork(
            placeholder: 'assets/loading.gif',          // shown while loading
            image: 'https://picsum.photos/250?image=9', // the real image
          ),
        ),
      ),
    );
  }
}
```

### Any widget as a placeholder and caching: `cached_network_image` package
Add the package in `pubspec.yaml` (`cached_network_image: ^3.x`), then:
```dart
CachedNetworkImage(
  placeholder: (context, url) => const CircularProgressIndicator(),
  imageUrl: 'https://picsum.photos/250?image=9',
);
```
Benefits: custom placeholder widget (like a spinner) **and** the image is **cached** on the device so it does not reload every time.

---

## 2.15 Material Buttons

Reference: https://material.io/components/buttons

> **Important update:** the old course slides used `RaisedButton`, `FlatButton`, `OutlineButton`. These are **deprecated/removed**. Use the modern ones:

| Old | Modern | Look |
|---|---|---|
| `RaisedButton` | `ElevatedButton` | Filled, with shadow (primary action) |
| `FlatButton` | `TextButton` | Text only (low emphasis) |
| `OutlineButton` | `OutlinedButton` | Border only (medium emphasis) |
| (none) | `IconButton` | Just an icon |
| (none) | `FloatingActionButton` | Round floating action |

```dart
Column(
  mainAxisAlignment: MainAxisAlignment.center,
  children: [
    ElevatedButton(
      onPressed: () => print('Elevated'),
      child: const Text('Elevated'),
    ),
    TextButton(
      onPressed: () => print('Text'),
      child: const Text('Text'),
    ),
    OutlinedButton(
      onPressed: () => print('Outlined'),
      child: const Text('Outlined'),
    ),
    IconButton(
      onPressed: () => print('Icon'),
      icon: const Icon(Icons.favorite),
    ),
    ElevatedButton.icon(
      onPressed: () {},
      icon: const Icon(Icons.send),
      label: const Text('Send'),
    ),
    const ElevatedButton(
      onPressed: null,                // null = disabled
      child: Text('Disabled'),
    ),
  ],
)
```

**Custom style:**
```dart
ElevatedButton(
  style: ElevatedButton.styleFrom(
    backgroundColor: Colors.deepPurple,
    foregroundColor: Colors.white,
    padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 16),
    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
  ),
  onPressed: () {},
  child: const Text('Styled'),
)
```

**Rule:** `onPressed: null` disables a button automatically.

---
---

# CHAPTER 2: Key takeaways
1. **Everything is a widget.** UIs are **trees** of nested widgets.
2. **StatelessWidget** = immutable, only `build()`. **StatefulWidget** = widget + State + `setState()`.
3. Use **`child`** for one widget and **`children`** for a list of widgets.
4. `Scaffold` gives you the page skeleton (`appBar`, `body`, ...). `MaterialApp` is the app root.
5. **Layout:** `Row`, `Column`, `Container`, `Padding`, `Center`, `Expanded`, `Flexible`.
6. **Navigation and feedback:** `Navigator.push`, `SnackBar`, `AppBar`, `TabBar`.
7. Extract your own widget classes to keep code clean; use `const` for performance.

---

# CHEAT SHEET

```dart
// App skeleton
void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
        home: Scaffold(
          appBar: AppBar(title: const Text('Title')),
          body: const Center(child: Text('Hello')),
        ),
      );
}

// Stateful skeleton
class Counter extends StatefulWidget {
  const Counter({super.key});
  @override
  State<Counter> createState() => _CounterState();
}
class _CounterState extends State<Counter> {
  int n = 0;
  @override
  Widget build(BuildContext context) => TextButton(
        onPressed: () => setState(() => n++),
        child: Text('$n'),
      );
}
```

| Need | Widget |
|---|---|
| Center something | `Center` |
| Space around | `Padding` / `Container(margin/padding)` |
| Vertical list | `Column` |
| Horizontal list | `Row` |
| Overlap widgets | `Stack` |
| Scrollable list | `ListView` / `ListView.builder` |
| Fill leftover space | `Expanded` |
| Proportions | `Flexible(flex: n)` |
| Tap detection with ripple | `InkWell` |
| Hide/show | `Visibility` / `Offstage` |
| Top bar | `AppBar` |
| Tabs | `DefaultTabController` + `TabBar` + `TabBarView` |

---

# COMMON ERRORS & FIXES

| Error | Cause | Fix |
|---|---|---|
| `flutter` is not recognized | PATH not set | Add `flutter\bin` to PATH, reopen terminal |
| Android licenses not accepted | Missing step | `flutter doctor --android-licenses` |
| `cmdline-tools` missing | SDK tool not installed | SDK Manager > SDK Tools > Command-line Tools |
| Screen doesn't update | Using Stateless / no `setState` | Use StatefulWidget and `setState` |
| `RIGHT OVERFLOWED BY ... PIXELS` | Row child too wide | Wrap in `Expanded` / `Flexible` |
| `Incorrect use of ParentDataWidget` | `Expanded` outside Row/Column | Put it directly inside Row/Column/Flex |
| `color` and `decoration` error | Both given to Container | Put color inside `BoxDecoration` |
| `No Material widget found` | InkWell/ListTile without Material | Wrap in `Material` or use `Scaffold` |
| `Navigator operation requested with a context that does not include a Navigator` | `context` is above `MaterialApp` | Use a child widget's context (inside `home:`) |
| TabBar crash | `length` mismatch | Same number of Tabs and TabBarView children |
| Hot reload not reflecting `main()` | State not rebuilt | Use hot restart (`R`) |

---

# QUIZ (answers at the end)

1. Name the three families of mobile development approaches and give one example of each.
2. Why can a hybrid app be slow?
3. What does "pixel-perfect" mean in Flutter?
4. Which file is the entry point, and what must it contain?
5. What does `flutter doctor` do?
6. Which folder holds ~99% of your code?
7. What's the difference between `child` and `children`?
8. Why doesn't the screen update in the "Stateless + counter++" experiment?
9. What does `setState` do?
10. A Row has three children and the middle one is a very long text that overflows. What fix?
11. `Flexible(flex: 2)`, `Flexible(flex: 3)`, `Flexible(flex: 1)` in a Column: what fraction does each take?
12. What's the difference between `Flexible` and `Expanded`?
13. Padding vs margin?
14. What replaces `RaisedButton`?
15. How do you disable a button?

<details>
<summary><strong>Answers</strong></summary>

1. Native (Swift/Kotlin), Hybrid (Ionic/Cordova), Compile-to-native (Flutter/React Native).
2. It runs inside a WebView (browser), adding a layer over native.
3. Identical visuals on iOS, Android and web because Flutter draws every pixel itself.
4. `lib/main.dart`, containing `main()`.
5. Checks your Flutter installation and reports missing tools.
6. `lib/`.
7. `child` takes one widget; `children` takes a list of widgets.
8. The variable changes but Flutter is never told to rebuild; Stateless widgets can't trigger rebuilds.
9. Runs your update and schedules a rebuild of the widget's `build()`.
10. Wrap the long text in `Expanded`.
11. 2/6 (1/3), 3/6 (1/2), 1/6.
12. Flexible lets the child be smaller (loose); Expanded forces it to fill (tight).
13. Padding = space inside the container; margin = space outside.
14. `ElevatedButton`.
15. `onPressed: null`.
</details>

---

# MINI-PROJECT: Put it all together

Build a **"Profile Card" app** that uses most of what you learned.

**Requirements**
1. `MaterialApp` + `Scaffold` + `AppBar` (title + one `IconButton` that shows a `SnackBar`).
2. A `ProfileCard` **custom StatelessWidget** with:
   - `Container` (rounded corners via `BoxDecoration`, padding, margin)
   - `Row` containing a `CircleAvatar` (or `Image.network`) and a `Column` of two `Text`s (name + job)
   - Use `Expanded` so long names don't overflow
3. A **StatefulWidget** "Like" button: an `IconButton` (heart) that toggles color/icon and increments a counter with `setState`.
4. A second screen opened with `Navigator.push` when tapping the card.
5. Bonus: `TabBar` with 3 tabs (Profile / Posts / Settings).

**Starter hint**
```dart
class ProfileCard extends StatelessWidget {
  final String name;
  final String job;
  const ProfileCard({super.key, required this.name, required this.job});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.all(16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.blue.shade50,
        borderRadius: BorderRadius.circular(16),
      ),
      child: Row(
        children: [
          const CircleAvatar(radius: 30, child: Icon(Icons.person)),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(name, style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                Text(job),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
```

---

# STUDY PLAN (to truly master it)

| Day | Focus |
|---|---|
| 1 | Chapter 1: install everything, get a clean `flutter doctor`, run the demo app |
| 2 | Stateless widgets, Container, Row, Column (examples 2.2 to 2.5, 2.10, 2.13) |
| 3 | Expanded / Flexible, Padding, custom widget (2.6, 2.11, 2.12) |
| 4 | StatefulWidget and `setState` (2.7): rebuild the counter by hand, without copying |
| 5 | AppBar, TabBar, SliverAppBar, Navigation (2.8, 2.9) |
| 6 | Images and Buttons (2.14, 2.15) |
| 7 | Mini-project + quiz |

> **Master's advice:** type code yourself instead of copy-pasting, break things on purpose to read the errors, and use the **Flutter Widget Catalog** (flutter.dev/docs/development/ui/widgets) as your dictionary.

**Next step:** Chapter 3 will likely cover lists (`ListView`), forms, navigation, and state management. You are now ready for it.
