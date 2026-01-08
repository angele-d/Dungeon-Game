/**
 * Money logic
 */

import config from "./money_config.js";

function updateMoneyDisplay() {
  /**
   * Updates the money display on the page
   * Parameters: None
   * Returns: None
   */
  const elt = document.querySelector("p.bank:nth-child(2)");
  const span = elt.children[0];
  elt.textContent = window.money;
  elt.prepend("$");

  const moneyBar = document.querySelector("div.moneybar");
  moneyBar.style.width = `${(money / config["initial-budget"]) * 100}%`;
}

function canAfford(cost) {
  /**
   * Checks if the player can afford a certain cost
   * Parameters:
   * cost - The cost to check against the player's money
   * Returns: Boolean indicating if the player can afford the cost
   */
  return window.money >= cost;
}
export { updateMoneyDisplay, canAfford };
