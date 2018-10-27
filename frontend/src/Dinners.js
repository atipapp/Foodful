import React, { Component } from "react";
import DinnerCard from "./DinnerCard";

class Dinners extends Component {
  render() {
    return (
      <div>
        <h2>Your upcoming dinners</h2>

        <div className="row align-items-center">
          {this.renderDinnerCard(this.getMockDinner())}
          {this.renderDinnerCard(this.getMockDinner())}
          {this.renderDinnerCard(this.getMockDinner())}
        </div>
      </div>
    );
  }

  renderDinnerCard(dinner) {
    return (
      <DinnerCard
        value={dinner}
      />
    );
  }

  getMockDinner() {
    return {
      title: "Ebedeles",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila"
    }
  }
}

export default Dinners;