import React, { Component } from "react";
import DinnerCard from "./DinnerCard";

class Dinners extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dinners: Array(3).fill(this.getMockDinner())
    };
  }


  render() {
    return (
      <div>
        <h2>Your upcoming dinners</h2>

        <div className="row align-items-center">
          {
            this.state.dinners.map(
              (dinner) => this.renderDinnerCard(dinner)
            )
          }
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