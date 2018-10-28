import React, { Component } from "react";
import DinnerCard from "./DinnerCard";

class Dinners extends Component {
  constructor(props) {
    super(props);
    this.handleDinnerRSVP = this.handleDinnerRSVP.bind(this);

    this.state = {
      dinners: this.getMockDinners(),
      currentUser: 'ABCD-EFGH-IJKL-MNOP',
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

  handleDinnerRSVP(dinner, isAccepted) {
    let currentDinners = this.state.dinners.slice();
    let currentUser = this.state.currentUser;

    currentDinners.forEach((currentDinner) => {
      if (currentDinner.id === dinner.id){
        currentDinner.guests.set(currentUser, isAccepted ? 'ACCEPTED' : 'DENIED')
      }
    })

    this.setState({
      dinners: currentDinners,
      currentUser: currentUser,
    });
  }

  renderDinnerCard(dinner) {
    return (
      <DinnerCard
        key={dinner.id}
        value={dinner}
        onRSVPClick={this.handleDinnerRSVP}
        statusForCurrentUser={
          dinner.guests.get(this.state.currentUser)
        }
      />
    );
  }

  getMockDinners() {
    return [{
      id: "1",
      title: "Ebedeles1",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila",
      guests: new Map([
        ['ABCD-EFGH-IJKL-MNOP', 'PENDING'],
      ])
    },
    {
      id: "2",
      title: "Ebedeles2",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila",
      guests: new Map([
        ['ABCD-EFGH-IJKL-MNOP', 'ACCEPTED'],
      ])
    },
    {
      id: "3",
      title: "Ebedeles3",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila",
      guests: new Map([
        ['ABCD-EFGH-IJKL-MNOP', 'ACCEPTED'],
      ])
    }]
  }
}

export default Dinners;