import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { fetchCategoryById } from '../../store/slices/categorySlice';
import ProductItem from './ProductItem';

const Products = () => {
  const { categoryId } = useParams();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCategoryById({ categoryId: categoryId }));
  }, [dispatch, categoryId]);

  const { selectedCategory } = useSelector((state) => state.categories);
  const getImagePath = (imageName) => `/coffee/${imageName}.jpg`;

  return ( <div className='mt-24'>
        {selectedCategory ? (
          <div className='flex-col'>
            <h1 className='font-kalnia text-4xl m-4 text-center'>{`Products related to ${selectedCategory.name}`}</h1>
            <div className='flex flex-wrap justify-evenly'>
            {selectedCategory.products.map(product => <ProductItem product={product} key={product.id} image={getImagePath(product.name)}/>)}
            </div>
          </div>
        ) : (
          <p>Loading...</p>
        )}
      </div>
  );
};

export default Products;
