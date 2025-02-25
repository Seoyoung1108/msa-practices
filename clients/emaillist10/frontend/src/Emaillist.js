import React from 'react';
import {Email_List} from './assets/scss/Emaillist.scss';
import Email from './Email';

function Emaillist({emails, deleteEmail}) {
    return (
        emails ?
            <ul
                className={Email_List}>
                {
                    emails.map((e, i) => <Email
                        key={i}
                        id={e.id}
                        firstName={e.firstName}
                        lastName={e.lastName}
                        email={e.email}
                        deleteEmail={deleteEmail}/>)
                }
            </ul> :
            null
    );
}

export default Emaillist;