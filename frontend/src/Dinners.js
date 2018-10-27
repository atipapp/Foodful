import React, { Component } from "react";
import DinnerCard from "./DinnerCard";
import { Grid } from "@material-ui/core";

class Dinners extends Component {
  render() {
    return (
      <div>
        <h2>Your upcoming dinners</h2>

        <div class="row align-items-center">
          {this.renderDinnerCard()}
          {this.renderDinnerCard()}
          {this.renderDinnerCard()}
        </div>
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