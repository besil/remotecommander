# remotecommander
A lightweight server for executing bash commands on a target machine using REST.

##Quickstart
Download the latest release:
```https://github.com/besil/remotecommander/releases```

Run the REST server on the target pc:
```bash
java -jar remotecommander.jar
```

Use GET && POST to retrieve result or submit new bash commands:
```bash
pi@raspberrypi:~ $ CMD="whoami"
pi@raspberrypi:~ $ wget -qO- --post-data="command=$CMD" 192.168.1.4:2220/command/submit
{"command":"whoami","ticketId":0}
pi@raspberrypi:~ $ wget -qO- 192.168.1.4:2220/result/0
{"command":"whoami","stdout":"pi\n","stderr":"","exitStatus":0}
pi@raspberrypi:~ $ CMD="sleep 10"
pi@raspberrypi:~ $ wget -qO- --post-data="command=$CMD" 192.168.1.4:2220/command/submit
{"command":"sleep 10","ticketId":1}
pi@raspberrypi:~ $ wget -qO- 192.168.1.4:2220/result/1
{"message":"Job pending. Ticket: 1","exitStatus":0}
pi@raspberrypi:~ $ wget -qO- 192.168.1.4:2220/result/latest
{"message":"Job pending. Ticket: 1","exitStatus":0}
pi@raspberrypi:~ $ wget -qO- 192.168.1.4:2220/result/latest
{"command":"sleep 10","stdout":"","stderr":"","exitStatus":0}
```

###Features
Results have a time to live: you can change the default (10) one using: ```--ttl <minutes>```
