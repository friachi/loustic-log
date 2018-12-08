#/bin/bash
curl -H "Content-Type: application/json" -d @lousticsession.json -X DELETE http://localhost:12000/ls/session/delete
