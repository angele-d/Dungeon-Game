document.addEventListener("DOMContentLoaded", () => {
  let list = document.querySelector("#leaderboard-list");
  let gameList = new Array(100).fill({
    filename: "filename",
    score: 1500,
    money: 500,
  });

  let url = window.location.pathname;
  console.log(url);
  url = url.replace("/leaderboard.html", "/game.html");

  gameList.forEach((game, i) => {
    const newElement = document.createElement("tr");
    newElement.classList.add("leaderboard-entry");
    newElement.innerHTML = `
    <th class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">${
      i + 1
    }</th>
    <td class="px-6 py-4 filename"><a href="${url}?load=${game.filename}">${
      game.filename
    }</a></td>
    <td class="px-6 py-4">${game.score}</td>
    <td class="px-6 py-4">$${game.money}</td>
  `;
    list.appendChild(newElement);
  });
});
