"""javascript
const myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");
myHeaders.append("Authorization", "••••••");

const raw = JSON.stringify({
  "rawText": "Spent 200 rs on blinkit today"
});

const requestOptions = {
  method: "POST",
  headers: myHeaders,
  body: raw, 
  redirect: "follow"
};

fetch("http://localhost:8080/api/ai-input", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
  """