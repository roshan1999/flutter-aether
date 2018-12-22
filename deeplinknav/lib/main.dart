import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(new MyApp());
}

class MyApp extends StatefulWidget {
  @override
  State createState() => new MyAppState();
}

class MyAppState extends State<MyApp> {
  String route;
  static const platform = const MethodChannel('geturi.com/route');
  void initState(){
    getRoute().then((String message){
      print("message = ");
              print(message);
      setState(() {
              this.route = message;
              
            });
    });
    print("route = ");
    print(route);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final routes = <String, WidgetBuilder>{
      // Shown when launched with plain intent.
      '/': (BuildContext ctx) => centeredText('Root'),
      // Shown when launched with known deep link.
      '/resetpassword': (BuildContext ctx) =>
          centeredText('Geturi'),
      // Shown when launched with another known deep link.
      '/yellow/brick/road': (BuildContext ctx) =>
          centeredText('Yellow brick road'),
    };
    return new MaterialApp(
      title: 'Flutter Demo',
      routes: routes,
      // Forces use of initial route from platform (otherwise it defaults to /
      // and platform's initial route is ignored).
      initialRoute: null,
      // Used when launched with unknown deep link.
      // May do programmatic parsing of routing path here.
      onGenerateRoute: (RouteSettings settings) => new MaterialPageRoute(
            builder: (BuildContext ctx) => centeredText('Not found'),
          ),
    );
  }

  Widget centeredText(String text) {
    return new Scaffold(body: new Center(child: new Text(text)));
  }

  Future<String> getRoute() async {
    String route;
    try{
    route = await platform.invokeMethod('getUri');
    }catch(e){
      print(e);
    }
    return route;
  }
}