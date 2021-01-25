import React, { useEffect, useState } from 'react';
import './Population.css';
import {POPULATION_API, MONGO_CONNECTION_STRING, MONGO_DB} from './Constants';
import {MongoClient} from 'mongodb';

function State(props) {
    const {name, pop} = props;
    return (<tr>
        <td>{name}</td>
        <td>{pop}</td>
    </tr>)
}

function Population() {
    const [data, setData] = useState([]);
    const [fullData, setFullData] = useState([]);
    const client = new MongoClient(MONGO_CONNECTION_STRING, {useNewUrlParser: true});

    // Get Population Data
    useEffect(() => {
        fetch(POPULATION_API).then((accept) => {
            let data = accept.json();
            return data;
        }).then((data) => {
            setData(data);
        });
    }, []);

    // Get Senators?
    useEffect(() => {
        console.log(MONGO_CONNECTION_STRING);
        console.log(client.connect);
        client.connect(err => {
            const collection = client.db(MONGO_DB).collection("senate");
            console.log(
                collection.find({state: "New York"})
            );
            const tempData = data.map((value) => {
                return {
                    name: value[0],
                    pop: value[1],
                    senators: collection.find({state: value[0]})
                };
            });
            setFullData(tempData);
            client.close();
        })
    }, [data])

    const states = data.slice(1).map((value) => {
        return <State name={value[0]} pop={value[1]} key={value[2]} />
    });
    return <div>
        <table>
            <thead>
                <tr>
                    <th>
                        Name
                    </th>
                    <th>
                        Population
                    </th>
                </tr>
            </thead>
            <tbody>
                {states}
            </tbody>
        </table>
    </div>
}

export default Population;
