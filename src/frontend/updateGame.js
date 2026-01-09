import { game_config } from "./game_config.js";
let { size } = game_config;
import { selectionPossible, selectGridCell } from "./game.js";

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

  let folds = new Map([
    ["woodwall", 0],
    ["stonewall", 0],
    ["poisontrap", 0],
    ["mine", 0],
    ["walltrap", 0],
    ["startingpoint", 0],
    ["treasure", 0],
  ]);

  for (let i = 0; i < size; i++) {
    for (let j = 0; j < size; j++) {
      const cell = document.createElement("div");

      // Count elements for sidebar display
      if (folds.has(gridData[i][j])) {
        folds.set(gridData[i][j], folds.get(gridData[i][j]) + 1);
      }

      switch (gridData[i][j]) {
        case "empty":
          cell.classList.add(
            "grid-cell",
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
            "grid-cell",
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
            "grid-cell",
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
        case "poisontrap":
          cell.classList.add(
            "grid-cell",
            "poisontrap",
            "bg-[#4a1e1e]",
            "relative"
          );
          innerSpan = document.createElement("span");
          innerSpan.classList.add("material-symbols-outlined", "text-red-500");
          innerSpan.textContent = "coronavirus";
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
        case "mine":
          cell.classList.add("grid-cell", "mine", "bg-[#4a1e1e]", "relative");
          innerSpan = document.createElement("span");
          innerSpan.classList.add("material-symbols-outlined", "text-red-500");
          innerSpan.textContent = "bomb";
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
        case "walltrap":
          cell.classList.add(
            "grid-cell",
            "walltrap",
            "bg-[#4a1e1e]",
            "relative"
          );
          innerSpan = document.createElement("span");
          innerSpan.classList.add("material-symbols-outlined", "text-red-500");
          innerSpan.textContent = "mist";
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
        case "startingpoint":
          cell.classList.add(
            "grid-cell",
            "startingpoint",
            "bg-black",
            "relative"
          );
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
          cell.classList.add(
            "grid-cell",
            "treasure",
            "bg-[#3a612c]",
            "relative"
          );
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

  // Update sidebar folds
  folds.forEach((count, elementType) => {
    const sidebarElement = document
      .getElementById(elementType)
      .querySelector(".folds");
    sidebarElement.textContent = `x${count}`;
  });
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
    hero[3] = parseFloat(hero[3]) * 100; // Convert to percentage

    const color = colors[index % colors.length];
    const heroDiv = document.createElement("div");
    heroDiv.classList.add("hero-info", "flex", "items-center", "gap-3");
    heroDiv.innerHTML = `
            <div class="flex items-center gap-3">
              <div class="relative">
                <div class="size-12 rounded-full overflow-hidden border-2 border-${color}-500 bg-surface-dark">
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

    if (
      hero[1] != null &&
      hero[2] != null &&
      !isNaN(hero[1]) &&
      !isNaN(hero[2]) &&
      hero[3] > 0
    ) {
      // Update on the grid
      const targetCell =
        document.querySelectorAll(".grid-cell")[
          parseInt(hero[1]) * size + parseInt(hero[2])
        ];
      targetCell.classList.add("relative");
      targetCell.innerHTML = heroPawns[hero[0]];
    }
  });
}

export { updateGrid, updateHeroes };
