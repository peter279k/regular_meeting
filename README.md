# reqular_meeting
regular meeting.

##### Design objectives and benefits
1 Convenient: No need to package an apk

2 Secure: access java code via webview javascript bridge is not secure

3 Does not need extra javascript library. Everything is RESTful service

## 2015/07/29
####Article
1	整理Apache Cordova(PhoneGap)存取硬體裝清單列表(ex: camera,GPS,...etc.)

2	將JavaScript解析出並懂的如何呼叫

3	測試呼叫PhoneGap JS APIs 的時間,與Android native API 做個比較

ex:	讀取100個通訊錄做比較.取得目前個人所在經緯度位置.

4	chart圖表,曲線圖可更換點的圖示(可能換一套繪圖library)

5	下禮拜不meeting.

## 2015/8/19
####Article
1 Performance measurement: native vs PhoneGap

2 Identify irregular cases from tested items

3 Static page only on local server

4 Mobile web performance

5 Mobile multimedia support: audio and video capture

6 HTML5 gaming performance on mobiles device


## 2015/8/14
####References
1 JavaScript performance on mobile environment: http://sealedabstract.com/rants/why-mobile-web-apps-are-slow/

2 Access local services using domains like xxx.local (mdns)

3 Survey mdns, and android’s network service discovery: see http://developer.android.com/training/connect-devices-wirelessly/nsd.html


## 2015/08/19
```bash
1 Performance measurement: native vs PhoneGap
```

```bash
2 irregular cases from tested items
```

4 Static page only on local server

5 Mobile web performance


## 2015/08/26
1 Preview screen: Intent or native API

```bash
2 AJAX URL access control: how to do it? Why and How?
```

```bash
3 Modified WebView in PhoneGap?
```

4 mdns

## 2015/09/06
```bash
1 Performance measurement: native vs PhoneGap
```

兩者執行時間其實沒有差很多，因為PhoneGap使用的仍是透過JS去呼叫原生對應的class 或是jar

```bash
2 Identify irregular cases from tested items
```

有兩個case比較有問題，一個是PhoneGap響鈴(navigatror.notification.beep(1))，與原生相較之下，原生快很多，反而PhoneGap要花上約一秒。

另一個則是讀取手機上的通訊錄，Native上讀取速度較慢，要花上一秒，而PhoneGap則花0.04秒，不過這兩個案例並沒有在開發上產生影響。

```bash
3 Modified WebView in PhoneGap?
```

PhoneGap基本上是包WebView，隨著版本演進和Android 系統有關，因此WebView基本上與Android native WebView沒有太大差別，不一樣的是，PhoneGap裡的WebView版本是固定的，不可隨意更改，若要更改，或是有更好的開發則須升級PhoneGap版本。

```bash
4 AJAX URL access control: how to do it? Why and How?
```

PhoneGap機制就是藉著WebView裡的JavaScript Bridge技術，與原生的API作互動，因此這個動作存在著風險問題，若沒有做好input validation，容易受到XSS(cross site scripting)，PhoneGap有鑑於此，在專案建立好之後，需要去設定config.xml，設定允許存取的外部網域，使用的是whitelist 機制，開發時就需要做外部存取網域管理，防止XSS攻擊。

reference : http://docs.phonegap.com/en/4.0.0/guide_appdev_whitelist_index.md.html

## 2015/09/15:(mDNS)
1 目前mDNS到了解定義，並試著在Ubuntu上架設avahi-daemon的服務，並成功使用sample service file做出使用本機(hostname): hostname.local ping 成功

去尋找Web browser Chrome 也做出相關的外掛程式, mDNS browser, 用來做mDNS discovery.

2 reference : 
mDNS原理: http://www.binkery.com/archives/318.html

mDNS browser: https://github.com/GoogleChrome/chrome-app-samples/tree/master/samples/mdns-browser

avahi-daemon: http://pjack1981.blogspot.tw/2012/07/avahi.html

hostname.local: http://coldnew.github.io/blog/2013/12/15_f6961.html

## 2015/09/16:(Mobile Web Performance)
1 目前正在看這本書: http://it-ebooks.info/book/6086/  (High Performance Mobile Web)

## 2015/09/22: javascript benchmark
1 Mozilla有針對JavaScript效能測試，出一款新的叫做Kraken

連結到測試的網址之後，會直接開始針對Javascript上的一些重要函式做測試，例如Date   類別，正規表示式，以及字串等。

WebKit(瀏覽器排版引擎)也針對JavaScript出了一款名叫SunSpider。

Google也有自己出一款JavaScript Benchmark，叫做V8 Benchmark Suite 

另外，Benchmark也可以自己寫，JavaScript有人針對這個編寫一套library叫做Benchmark.js可以測量出自己客製化的相關函式或是動作。

2 Reference:

Kraken: http://www.openfoundry.org/tw/news/8145?task=view

http://krakenbenchmark.mozilla.org/

SunSpider: https://www.webkit.org/perf/sunspider-1.0.2/sunspider-1.0.2/driver.html

V8 Benchmark Suite : http://v8.googlecode.com/svn/data/benchmarks/v7/run.html

octane: http://octane-benchmark.googlecode.com/svn/latest/index.html

Benchmark.js: https://github.com/bestiejs/benchmark.js

(詳細會把手機與桌上web browse跑完三個常用的benchmark結果r整理成投影片)

## 2015/09/30 : Service type has only limited choices: ._http._tcphost, with port numbers
1 在這篇http://developer.android.com/training/connect-devices-wirelessly/nsd.html

文章中有特別提到關於service type，能夠使用的要去The International Assigned Numbers Authority(國際網路協定註冊中心)去看可以使用的清單列表。只要上面有的，都可以使用。

http://www.obsessivelyandroid.com/?p=145 文章中提到

(其實可以自行定義協定名稱，僅限於開發與實驗時使用，不過當要上架到APP store時，還是必須要去註冊自己定義的協定名稱)

SERVICE_TYPE格式 : _[protocol]._[transportlayer]. 

the IANA list of service names and port numbers

## 2015/10/05 : embedded http server (X) prevent using cordova to implement app```bash(deprecated)```
1 https://github.com/floatinghotpot/cordova-httpd

2 這個可以實現跟老師說的Apache HTTP server 一樣的功能嗎?

3 Is it possible to embed a local web server into phonegap project?

http://stackoverflow.com/questions/24958990/is-it-possible-to-embed-a-local-web-server-into-phonegap-project

## 2015/10/14 : decompose UI response time of web-based apps
1 300ms on mobile apps (include hybrid app)

http://www.joshmorony.com/how-to-remove-the-300ms-tap-delay-in-html5-mobile-apps/

http://www.smashingmagazine.com/2015/03/better-browser-input-events/

http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2015/03/02-android-opt.png

解法 : Fastclick

https://github.com/ftlabs/fastclick

Demo : Test button : http://patrickhlauke.github.io/touch/tests/event-listener.html

在Chrome 45 (行動版本下這個問題已經沒有)

## 2015/10/14 : CPU performance compared to desktop PC CPUs
目前還是沒有找到 用不同CPU 跑JavaScript相關的benchmark

## 2015/10/14 : nsd(server socket)

http://www.obsessivelyandroid.com/?p=145 (nsd server)

目前研讀完，差實做部分

## 2015/10/15 & 2015/10/22
1 Understand how UI latency measurement works, and implement our own tools

## 2015/10/01 & 2015/10/08
1 CPU performance compared to desktop PC CPUs

2 Mobile javascript benchmark: javascript vs native computation power benchmark

3 UI response benchmark (w/ a external camera)

4 decompose UI response time of web-based apps

5 [optional] interpreter (php, python, ruby, …) performances on mobile devices
	Mobile device: native client vs javascript client
	Simple Socket server that accepts HTTP connections and responds HELLO, WORLD!
	Server: mDNS
	Client: Browser - connect to xxxx.local (not working?)

## 2015/09/24
1 Service type has only limited choices: ._http._tcphost, with port numbers

2 Working with browser
	
## 2015/09/17
1 NSD works on Android: confirm that built-in browser and webkit/webview are able to locate the service: http://developer.android.com/training/connect-devices-wirelessly/nsd.html

2 servicename.local

3 application sharing in the LAN

4 mobile web performance: javascript benchmark

## 2015/11/06
1 選用一個前端Framework,與andoird native ui 列出共同元件列表

2 把(nsd server + socket server)可以使用瀏覽器開啟。

## 2015/11/18
1 nsd server + socket server 可以使用瀏覽器打開。(更新至投影片)
2 前端jQuery Mobile元件與原生Android Widget 元件(列表整裡成表格)

## 2015/11/19
part 1

server * 3 (cordova NSD server, native NSD server, bonjour server (jmdns/avahi) )

client * 4 (cordova NSD client, native NSD client, bonjour client (jmdns/avahi), generic client)

queried name * 2: service name, service name + “.local”

WebView capabilities: is it the same powerful as chrome or other browser

javascript, html5, performance

part 2

UI latency measurement frameworks

native vs jQuery mobile

screen shots

components, touch event

## 2015/12/02
part1

整理WebView支援能力的ppt，HTML5 的程度

part2

有Bonjour Browser for Windows這類工具

Bonjour Server for Windows?

新增Bonjour browser Demo