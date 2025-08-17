# Friends API

Base path: `/api/friends`

Auth: Bearer JWT required for all endpoints below.

- GET `/api/friends` — List my friends (public user fields)
- POST `/api/friends` — Body: `{ "friendId": number }`
- DELETE `/api/friends/{friendId}` — Remove friend

Example (frontend):

```js
const token = localStorage.getItem('token');
// list
fetch('/api/friends', { headers: { Authorization: `Bearer ${token}` } })
  .then(r=>r.json()).then(console.log);
// add
fetch('/api/friends', { method:'POST', headers:{ 'Content-Type':'application/json', Authorization:`Bearer ${token}` }, body: JSON.stringify({ friendId: 2 }) });
// remove
fetch('/api/friends/2', { method:'DELETE', headers:{ Authorization:`Bearer ${token}` } });
```
