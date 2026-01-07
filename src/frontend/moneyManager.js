/**
 * Money logic
 */

import config from "./money_config.js";

let money = config["initial-budget"];

function updateMoneyDisplay() {
  /**
   * Updates the money display on the page
   * Parameters: None
   * Returns: None
   */
  const elt = document.querySelector("p.bank:nth-child(2)");
  const span = elt.children[0];
  elt.textContent = money;
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
  return money >= cost;
}

function deductMoney(cost) {
  /**
   * Deducts a certain cost from the player's money and updates the display
   * Parameters:
   * cost - The cost to deduct from the player's money
   * Returns: None
   */
  money -= cost;
  updateMoneyDisplay();
}
export { updateMoneyDisplay, canAfford, deductMoney };
