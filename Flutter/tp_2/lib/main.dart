import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Exemple de boutons"),
        ),
        body: Center(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextButton(
                  onPressed: () {
                    print("Premier bouton clique");
                  },
                  child: const Text("Premier bouton"),
                ),

                const SizedBox(height: 50),

                TextButton.icon(
                  onPressed: () {
                    print("Bouton Call Me cliqué");
                  },
                  icon: const Icon(Icons.call),
                  label: const Text("Call Me"),
                ),

                const SizedBox(height: 50),

                // Replaces RaisedButton
                ElevatedButton(
                  onPressed: () => print('RaisedButton was clicked'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.redAccent,
                    foregroundColor: Colors.white,
                    overlayColor: Colors.blue,
                    disabledForegroundColor: Colors.brown,
                    disabledBackgroundColor: Colors.grey,
                  ),
                  child: const Text('My RaisedButton'),
                ),

                const SizedBox(height: 100),

                // Replaces RaisedButton.icon
                ElevatedButton.icon(
                  onPressed: () => print('RaisedButton.icon was clicked'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.redAccent,
                    foregroundColor: Colors.white,
                  ),
                  icon: const Icon(
                    Icons.volume_up,
                    color: Colors.yellowAccent,
                  ),
                  label: const Text('Increase'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}