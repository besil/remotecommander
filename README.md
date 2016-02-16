# remotecommander

###Quickstart


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
pi@raspberrypi:~ $
```
