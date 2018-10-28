import React, { Component } from "react";
import DinnerCard from "./DinnerCard";

class Dinners extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dinners: this.getMockDinners()
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

  handleDinnerRSVP(dinner, isAccepted){
    console.log('Dinner rsvp changed on dinner ' + dinner.id + ' ' + isAccepted);
  }

  renderDinnerCard(dinner) {
    return (
      <DinnerCard
        key={dinner.id}
        value={dinner}
        onRSVPClick={this.handleDinnerRSVP}
      />
    );
  }

  getMockDinners() {
    return [{
      id: "1",
      title: "Ebedeles1",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila"
    },
    {
      id: "2",
      title: "Ebedeles2",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila"
    },
    {
      id: "3",
      title: "Ebedeles3",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila"
    }]
  }
}

export default Dinners;