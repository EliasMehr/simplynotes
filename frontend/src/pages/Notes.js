import React, { Component } from "react";

export default class Notes extends Component {


    render() {
        return (
            <div>
                <h2>Notes</h2>
                <div className="note-container">
                    <h1>Välkommen BrEh BeniM Boi HeartEmoji Laughtilicry2deathemoji</h1>
                    <h1>{JSON.parse(localStorage.getItem('user')).username}</h1>
                    <h3>{JSON.parse(localStorage.getItem('user')).email}</h3>
                    <p>xDnoteLulXD</p>
                </div>
            </div>
        );
    }
}