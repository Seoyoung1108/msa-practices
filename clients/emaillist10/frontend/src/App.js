import React, {useEffect, useState} from 'react';
import RegisterForm from './RegisterForm';
import SearchBar from './SearchBar';
import Emaillist from './Emaillist';
import axios from 'axios';

import './assets/scss/App.scss';

function App() {
    const [emails, setEmails] = useState(null);

    const addEmail = async (email) => {
        try {
            const response = await axios.post(API_URL, email);
            const jsonResult = response.data;

            setEmails([jsonResult.data, ...emails]);
        } catch(err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }   
    }

    const fetchEmails = async (keyword) => {
        try {
            const response = await axios.get(`${API_URL}?kw=${keyword ? keyword : ''}`);
            const jsonResult = response.data;

            setEmails(jsonResult.data);
        } catch(err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        } 
    }

    const deleteEmail = async (id) => {
        try {
            const response = await axios.delete(`${API_URL}/${id}`);
            const jsonResult = response.data;

            setEmails(emails.filter(e => e.id !== jsonResult.data));
        } catch(err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }        
    }

    useEffect(() => {
        fetchEmails();
    }, []);

    return (
        <div id={'App'}>
            <RegisterForm addEmail={addEmail}/>
            <SearchBar fetchEmails={fetchEmails}/>
            <Emaillist
                emails={emails}
                deleteEmail={deleteEmail}/>
        </div>
    );
}

export default App;