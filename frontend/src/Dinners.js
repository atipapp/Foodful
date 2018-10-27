import React, { Component } from "react";
import DinnerCard from "./DinnerCard";

class Dinners extends Component {
  render() {
    return (
      <div>
        <h2>Your upcoming dinners</h2>
        {this.renderDinnerCard()}

      </div>
    );
  }

  renderDinnerCard() {
    return (
      <DinnerCard />
    );
  }
}

export default Dinners;