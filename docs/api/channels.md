# Channels API

Base path: `/api/channels`

Auth: Bearer JWT required for protected endpoints.

- GET `/api/channels` — List channels I joined/own
- POST `/api/channels` — Body: `{ "name": string, "memberIds": number[] }` (owner = current user)
- POST `/api/channels/{id}/members` — Body: `{ "userId": number }` (add member)
- DELETE `/api/channels/{id}/members/{userId}` — Remove member
- GET `/api/channels/{id}/members` — List member userIds

Example (frontend):

```js
const token = localStorage.getItem('token');
// create
fetch('/api/channels', { method:'POST', headers:{ 'Content-Type':'application/json', Authorization:`Bearer ${token}` }, body: JSON.stringify({ name: 'study group', memberIds:[2,3] }) })
  .then(r=>r.json()).then(console.log);
// my channels
fetch('/api/channels', { headers: { Authorization: `Bearer ${token}` } })
  .then(r=>r.json()).then(console.log);
// add member
fetch('/api/channels/1/members', { method:'POST', headers:{ 'Content-Type':'application/json', Authorization:`Bearer ${token}` }, body: JSON.stringify({ userId: 4 }) });
// remove member
fetch('/api/channels/1/members/4', { method:'DELETE', headers:{ Authorization:`Bearer ${token}` } });
```
