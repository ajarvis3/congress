import React, { useEffect, useState } from 'react';
import './Population.css';
import {POPULATION_API, SENATE_API, NON_STATES} from './Constants';

function State(props) {
    const {name, pop, sen1, sen2} = props;
    return (<tr>
        <td>{name}</td>
        <td>{pop}</td>
        <td>{sen1}</td>
        <td>{sen2}</td>
    </tr>)
}

function Population() {
    const [data, setData] = useState([]);
    const [fullData, setFullData] = useState([]);

    // Get Population Data
    useEffect(() => {
        fetch(POPULATION_API).then((accept) => {
            let data = accept.json();
            return data;
        }).then((data) => {
            data = data.filter((value) => {
                const state = value[0];
                const res = !NON_STATES.includes(state);
                return res;
            });
            data.sort((a, b) => {
                return a[0].localeCompare(b[0]);
            });
            data = data.map((value) => {
                return {
                    state: value[1],
                    pop: value[0]
                }
            });
            setData(data);
        });
    }, []);

    // Get Senators?
    useEffect(() => {
        if (data.length === 0) {
            return;
        }
        fetch(SENATE_API).then((accept) => {
            let data = accept.json();
            return data;
        }).then((senate) => {
            console.log(senate);
            senate.sort((a, b) => {
                return a.state.localeCompare(b.state);
            });
            const tempData = data.map((value, index) => {
                return {
                    state: value.state,
                    pop: value.pop,
                    sen1: senate[index * 2],
                    sen2: senate[index * 2 + 1]
                }
            });
            setFullData(tempData);
        });
    }, [data])

    const states = fullData.map((value) => {
        return <State 
                    name={value.state} 
                    pop={value.pop} 
                    sen1={value.sen1}
                    sen2={value.sen2}
                    key={value[2]} />
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
                    <th>
                        Senator 1
                    </th>
                    <th>
                        Senator 2
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
