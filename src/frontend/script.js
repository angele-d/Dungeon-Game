import { game_config } from "./game_config.js";
let { size } = game_config;

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
        "empty",
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
    (Array.from(elt.classList).indexOf("empty") == -1 &&
      selectedElement != "eraser") ||
    (Array.from(elt.classList).indexOf("empty") != -1 &&
      selectedElement == "eraser") ||
    !canAfford(config[selectedElement]) ||
    (selectedElement == "treasure" && window.treasurePlaced) ||
    (selectedElement == "spawn" && window.spawnPlaced)
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

  if (selectedElement == "eraser") {
    sendAddElement(stompClient, "empty", cell.dataset.x, cell.dataset.y);
  } else {
    sendAddElement(
      stompClient,
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
 * IA change
 */

function onSelectAIChange(elt) {
  /**
   * Handles the change of AI from the dropdown menu
   * Parameters:
   * elt - The DOM element of the select dropdown
   * Returns: None
   */
  sendChangeAI(stompClient, elt.value);
}

function changeAI(ai) {
  window.ai = ai;
}

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

  document.querySelector("#ai-menu").value = "BFS";
  document.querySelector("#ai-menu").addEventListener("change", (event) => {
    onSelectAIChange(event.target);
  });

  document.querySelector("#next-button").addEventListener("click", () => {
    if (window.gameLaunched) {
      sendNextStep(stompClient);
    } else {
      sendLaunchGame(stompClient);
    }
  });
});

export { selectionPossible, selectGridCell };
