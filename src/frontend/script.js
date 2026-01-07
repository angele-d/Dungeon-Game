import { game_config } from "./gameConfig.js";
const size = game_config.size;

function generateGrid() {
  /**
   * Generate the cells of a size x size grid on the page
   * Parameters: None
   * Returns: None
   */
  const gridElement = document.querySelector(".grid");
  gridElement.innerHTML = "";
  for (let i = 0; i < size; i++) {
    for (let j = 0; j < size; j++) {
      const cell = document.createElement("div");
      cell.classList.add(
        "w-12",
        "h-12",
        "border",
        "border-[#1e2d36]",
        "grid-cell"
      );
      cell.dataset.x = "" + i;
      cell.dataset.y = "" + j;
      cell.addEventListener("click", () => selectGridCell(cell));
      gridElement.appendChild(cell);
    }
  }
}

import { canAfford, deductMoney, updateMoneyDisplay } from "./moneyManager.js";
import config from "./money_config.js";

/**
 * Element selection and placement logic
 */

let treasurePlaced = false;
let spawnPlaced = false;

let selectedElement = null;
let selectedDOM = null;

function addElement(elt) {
  /**
   * Handles the selection of an element from the sidebar
   * Parameters:
   * elt - The DOM element that was clicked
   * Returns: None
   */
  if (selectedDOM) selectedDOM.classList.remove("selected-cell");
  selectedElement = elt.id;
  selectedDOM = elt;
  elt.classList.add("selected-cell");
}

function selectionPossible(elt) {
  /**
   * Checks if the player can select the current selected element on that cell
   * Parameter:
   * elt - The DOM element that was hovered
   * Returns: None
   */
  if (!elt) return;

  if (
    (elt.classList != "w-12 h-12 border border-[#1e2d36] grid-cell" &&
      selectedElement != "eraser") ||
    (elt.classList == "w-12 h-12 border border-[#1e2d36] grid-cell" &&
      selectedElement == "eraser") ||
    !canAfford(config[selectedElement]) ||
    (selectedElement == "treasure" && treasurePlaced) ||
    (selectedElement == "spawn" && spawnPlaced)
  ) {
    elt.classList.add("not-allowed-cell");
  }
}

function make2digits(num) {
  /**
   * Formats a number to be at least two digits with leading zeros
   * Parameters:
   * num - The number to format
   * Returns: String representation of the number with at least two digits
   */
  return num.toString().padStart(2, "0");
}

function selectGridCell(cell) {
  /**
   * Handles the placement of the selected element onto the grid cell
   * Parameters:
   * cell - The DOM element of the grid cell that was clicked
   * Returns: None
   */
  if (
    !canAfford(config[selectedElement]) ||
    cell.classList.contains("not-allowed-cell")
  )
    return;
  let innerSpan, innerDiv;
  deductMoney(config[selectedElement]);
  let folds = selectedDOM.querySelector(".folds");
  if (folds) {
    folds.textContent = `x${make2digits(
      parseInt(folds.textContent.slice(1)) + 1
    )}`;
  }

  // switch (selectedElement) {
  //   case "wood-wall":
  //     cell.classList.add(
  //       "wood-wall",
  //       "bg-[#3d2e24]",
  //       "relative",
  //       "border-amber-900/30"
  //     );
  //     innerSpan = document.createElement("span");
  //     innerSpan.classList.add("material-symbols-outlined", "text-amber-700");
  //     innerSpan.textContent = "door_front";
  //     innerDiv = document.createElement("div");
  //     innerDiv.classList.add(
  //       "absolute",
  //       "inset-0",
  //       "flex",
  //       "items-center",
  //       "justify-center",
  //       "opacity-40"
  //     );
  //     innerDiv.appendChild(innerSpan);
  //     cell.appendChild(innerDiv);
  //     break;
  //   case "stone-wall":
  //     cell.classList.add("stone-wall", "bg-[#2a3f4a]", "relative");
  //     innerSpan = document.createElement("span");
  //     innerSpan.classList.add("material-symbols-outlined", "text-slate-400");
  //     innerSpan.textContent = "fort";
  //     innerDiv = document.createElement("div");
  //     innerDiv.classList.add(
  //       "absolute",
  //       "inset-0",
  //       "flex",
  //       "items-center",
  //       "justify-center",
  //       "opacity-40"
  //     );
  //     innerDiv.appendChild(innerSpan);
  //     cell.appendChild(innerDiv);
  //     break;
  //   case "spike":
  //     cell.classList.add("spike", "bg-[#4a1e1e]", "relative");
  //     innerSpan = document.createElement("span");
  //     innerSpan.classList.add("material-symbols-outlined", "text-red-700");
  //     innerSpan.textContent = "dangerous";
  //     innerDiv = document.createElement("div");
  //     innerDiv.classList.add(
  //       "absolute",
  //       "inset-0",
  //       "flex",
  //       "items-center",
  //       "justify-center",
  //       "opacity-40"
  //     );
  //     innerDiv.appendChild(innerSpan);
  //     cell.appendChild(innerDiv);
  //     break;
  //   case "eraser":
  //     let keys = Object.keys(config);
  //     let erased = Array.from(cell.classList).filter(
  //       (elt) => keys.indexOf(elt) != -1
  //     );
  //     deductMoney(-config[erased]);
  //     let folds = document
  //       .querySelector(".cell#" + erased)
  //       .querySelector(".folds");
  //     if (folds) {
  //       folds.textContent = `x${make2digits(
  //         Math.max(0, parseInt(folds.textContent.slice(1)) - 1)
  //       )}`;
  //     }

  //     if (erased == "treasure") treasurePlaced = false;
  //     if (erased == "spawn") spawnPlaced = false;

  //     cell.innerHTML = "";
  //     cell.className = "w-12 h-12 border border-[#1e2d36] grid-cell";
  //     break;
  //   case "spawn":
  //     cell.classList.add("spawn", "bg-black", "relative");
  //     innerSpan = document.createElement("span");
  //     innerSpan.classList.add("material-symbols-outlined", "text-white");
  //     innerSpan.textContent = "skull";
  //     innerDiv = document.createElement("div");
  //     innerDiv.classList.add(
  //       "absolute",
  //       "inset-0",
  //       "flex",
  //       "items-center",
  //       "justify-center"
  //     );
  //     innerDiv.appendChild(innerSpan);
  //     cell.appendChild(innerDiv);
  //     spawnPlaced = true;
  //     break;
  //   case "treasure":
  //     cell.classList.add("treasure", "bg-[#3a612c]", "relative");
  //     innerSpan = document.createElement("span");
  //     innerSpan.classList.add("material-symbols-outlined", "text-yellow-400");
  //     innerSpan.textContent = "savings";
  //     innerDiv = document.createElement("div");
  //     innerDiv.classList.add(
  //       "absolute",
  //       "inset-0",
  //       "flex",
  //       "items-center",
  //       "justify-center"
  //     );
  //     innerDiv.appendChild(innerSpan);
  //     cell.appendChild(innerDiv);
  //     treasurePlaced = true;
  //     break;
  // }

  console.log(cell.dataset);
  if (selectedElement == "eraser") {
    sendAddElement(stompClient, "1", "empty", cell.dataset.x, cell.dataset.y);
  } else {
    sendAddElement(
      stompClient,
      "1",
      selectedElement,
      cell.dataset.x,
      cell.dataset.y
    );
  }
}

const grid = document.querySelector(".grid");
const cells = document.querySelector(".space-y-3");

document.addEventListener("pointerdown", (event) => {
  if (!grid.contains(event.target) && !cells.contains(event.target)) {
    if (selectedDOM) {
      selectedDOM.classList.remove("selected-cell");
    }
    selectedElement = null;
    selectedDOM = null;
  }
});

/**
 * Initialize the grid, connect to server and set up event listeners on DOM content loaded
 */

import {
  getClient,
  connectClient,
  sendAddElement,
  sendChangeAI,
  sendLaunchGame,
  sendNextStep,
} from "./connection.js";

let stompClient;

document.addEventListener("DOMContentLoaded", () => {
  generateGrid();
  updateMoneyDisplay();
  stompClient = getClient();
  connectClient(stompClient);
  document.querySelectorAll("div.cell").forEach((element) => {
    element.addEventListener("click", () => {
      addElement(element);
    });
  });

  document.querySelectorAll("div.grid-cell").forEach((cell) => {
    cell.addEventListener("mouseover", () => {
      selectionPossible(cell);
    });
    cell.addEventListener("mouseout", () => {
      cell.classList.remove("not-allowed-cell");
    });
  });
});
