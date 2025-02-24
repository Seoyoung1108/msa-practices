import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Header from "./Header";
import ImageList from "./ImageList";
import * as styles from '../../assets/scss/component/gallery/Gallery.scss';

export default function Index() {
    const [images, setImages] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const response = await axios.get('/api/gallery');
                const jsonResult = response.data;

                setImages(jsonResult.data);
            } catch (err) {
                console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
            }
        })();
    }, []);

    const addImage = async (comment, file) => {
        try {
            const formData = new FormData();
            formData.append('comment', comment);
            formData.append('file', file);

            const response = await axios.post('/api/gallery', formData, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'multipart/form-data'
                }
            });
            const jsonResult = response.data;

            setImages([jsonResult.data, ...images]);
        } catch (err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }
    };

    const deleteImage = async (no) => {
        try {
            const response = await axios.delete(`/api/gallery/${no}`);
            const jsonResult = response.data;

            setImages(images.filter((img) => img.id !== jsonResult.data));
        } catch (err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }
    };

    return (
        <div className={styles.Gallery}>
            <Header addImage={addImage}/>
            <ImageList images={images} deleteImage={deleteImage}/>
        </div>
    )
}