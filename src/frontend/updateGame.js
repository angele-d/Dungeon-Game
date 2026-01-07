import { game_config } from "./game_config.js";
let { size } = game_config;
import { selectionPossible, selectGridCell } from "./script.js";

function updateGrid(gridData) {
  /**
   * Updates the game grid based on the provided grid data.
   * Parameters:
   * gridData - A 2D array representing the current state of the grid.
   * Returns: None
   */
  const grid = document.querySelector(".grid");
  grid.innerHTML = "";
  let innerSpan, innerDiv;

  for (let i = 0; i < size; i++) {
    for (let j = 0; j < size; j++) {
      const cell = document.createElement("div");
      switch (gridData[i][j]) {
        case "empty":
          cell.classList.add(
            "empty",
            "w-12",
            "h-12",
            "border",
            "border-[#1e2d36]",
            "grid-cell"
          );
          break;
        case "woodwall":
          cell.classList.add(
            "w-12",
            "h-12",
            "border",
            "border-[#1e2d36]",
            "grid-cell",
            "woodwall",
            "bg-[#3d2e24]",
            "relative",
            "border-amber-900/30"
          );
          innerSpan = document.createElement("span");
          innerSpan.classList.add(
            "material-symbols-outlined",
            "text-amber-700"
          );
          innerSpan.textContent = "door_front";
          innerDiv = document.createElement("div");
          innerDiv.classList.add(
            "absolute",
            "inset-0",
            "flex",
            "items-center",
            "justify-center",
            "opacity-40"
          );
          innerDiv.appendChild(innerSpan);
          cell.appendChild(innerDiv);
          break;
        case "stonewall":
          cell.classList.add(
            "w-12",
            "h-12",
            "border",
            "border-[#1e2d36]",
            "grid-cell",
            "stonewall",
            "bg-[#2a3f4a]",
            "relative"
          );
          innerSpan = document.createElement("span");
          innerSpan.classList.add(
            "material-symbols-outlined",
            "text-slate-400"
          );
          innerSpan.textContent = "fort";
          innerDiv = document.createElement("div");
          innerDiv.classList.add(
            "absolute",
            "inset-0",
            "flex",
            "items-center",
            "justify-center",
            "opacity-40"
          );
          innerDiv.appendChild(innerSpan);
          cell.appendChild(innerDiv);
          break;
        case "spike":
          cell.classList.add("spike", "bg-[#4a1e1e]", "relative");
          innerSpan = document.createElement("span");
          innerSpan.classList.add("material-symbols-outlined", "text-red-700");
          innerSpan.textContent = "dangerous";
          innerDiv = document.createElement("div");
          innerDiv.classList.add(
            "absolute",
            "inset-0",
            "flex",
            "items-center",
            "justify-center",
            "opacity-40"
          );
          innerDiv.appendChild(innerSpan);
          cell.appendChild(innerDiv);
          break;
        case "spawn":
          cell.classList.add("spawn", "bg-black", "relative");
          innerSpan = document.createElement("span");
          innerSpan.classList.add("material-symbols-outlined", "text-white");
          innerSpan.textContent = "skull";
          innerDiv = document.createElement("div");
          innerDiv.classList.add(
            "absolute",
            "inset-0",
            "flex",
            "items-center",
            "justify-center"
          );
          innerDiv.appendChild(innerSpan);
          cell.appendChild(innerDiv);
          window.spawnPlaced = true;
          break;
        case "treasure":
          cell.classList.add("treasure", "bg-[#3a612c]", "relative");
          innerSpan = document.createElement("span");
          innerSpan.classList.add(
            "material-symbols-outlined",
            "text-yellow-400"
          );
          innerSpan.textContent = "savings";
          innerDiv = document.createElement("div");
          innerDiv.classList.add(
            "absolute",
            "inset-0",
            "flex",
            "items-center",
            "justify-center"
          );
          innerDiv.appendChild(innerSpan);
          cell.appendChild(innerDiv);
          window.treasurePlaced = true;
          break;
      }
      cell.dataset.x = "" + i;
      cell.dataset.y = "" + j;
      document.querySelector(".grid").appendChild(cell);
      cell.addEventListener("click", () => selectGridCell(cell));
      cell.addEventListener("mouseover", () => {
        selectionPossible(cell);
      });
      cell.addEventListener("mouseout", () => {
        cell.classList.remove("not-allowed-cell");
      });
    }
  }
}

import { heroPawns } from "./heroPawns.js";

function updateHeroes(heroesData) {
  /**
   * Updates the heroes on the grid based on the provided heroes data.
   * Parameters:
   * heroesData - An array of hero objects with properties: type, col, row, health.
   * Returns: None
   */

  // Update on the information panel
  const infoPanel = document.querySelector(".info-panel");
  infoPanel.innerHTML = ""; // Clear existing info

  let colors = ["green", "blue", "red", "purple", "yellow"];
  heroesData.forEach((hero, index) => {
    const color = colors[index % colors.length];
    const heroDiv = document.createElement("div");
    heroDiv.classList.add("hero-info", "flex", "items-center", "gap-3");
    heroDiv.innerHTML = `
            <div class="flex items-center gap-3">
              <div class="relative">
                <div class="size-12 rounded-full overflow-hidden border-2 border-green-500 bg-surface-dark">
                  <img alt="Healer Avatar" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCAAkqwEGSlT9FyPrvC-JtetAQKnVRGVpbT4uv0jeXJFcioU7jHVxWR3wOVxAq-Gc6To23Xbb7nEY38x0POiCInnyywW17QcvkjVIDHd0XDMSnNgDkphzFUt5UdScQUJs7RkP1K-QXrStZPkO9HMb7leB4bcPa_B9nRt-ldrtq5kGD1ZmY7-1bDAWD1Wa6qdYa41qr_wQ9t4h_3WuN1MvQA9gsZsEGSD89xVRFNPO95eVy4VE14oXhty8K44gXGQsHAmgowteqIn5W9">
                </div>
                <div class="absolute -bottom-1 -right-1 bg-surface-dark rounded-full p-0.5 border border-surface-border">
                  <span class="material-symbols-outlined text-${color}-500 text-xs block">health_and_safety</span>
                </div>
              </div>
              <div class="flex-1 min-w-0">
                <div class="flex justify-between items-center mb-1">
                  <p class="text-sm font-bold text-white truncate">
                    ${hero[0]}
                  </p>
                  <span class="text-xs text-${color}-400 font-mono">${hero[3]}%</span>
                </div>
                <div class="w-full h-1.5 bg-surface-border rounded-full overflow-hidden">
                  <div class="h-full bg-${color}-500" style="width: ${hero[3]}%;"></div>
                </div>
              </div>
            </div>`;
    infoPanel.appendChild(heroDiv);

    // Update on the grid
    const targetCell =
      document.querySelectorAll(".grid-cell")[
        parseInt(hero[2]) * size + parseInt(hero[1])
      ];
    targetCell.classList.add("relative");
    targetCell.innerHTML = heroPawns[hero[0]];
  });
}

export { updateGrid, updateHeroes };
