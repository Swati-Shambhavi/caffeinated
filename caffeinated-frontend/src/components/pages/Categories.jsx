import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCategories } from '../../store/slices/categorySlice';
import CategoryItem from './CategoryItem';
import teaImage from '../../../public/categories/tea.jpg';
import coffeeImage from '../../../public/categories/coffee.jpg';
import chocolateImage from '../../../public/categories/chocolate.jpg';
import cookieImage from '../../../public/categories/cookies.jpg';

const Categories = ({style}) => {
  const { data, error, operationStatus } = useSelector((state) => state.categories);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);

  const getImage = (name) => {
    name = name.toLowerCase();
    if (name === 'tea') {
      return teaImage;
    } else if (name === 'coffee') {
      return coffeeImage;
    } else if (name === 'chocolate') {
      return chocolateImage;
    } else if (name === 'cookies') {
      return cookieImage;
    }
  };

  return (
    <div className={`${style} text-center`}>
      <h1 className='text-3xl pt-4 font-kalnia font-bold'>Indulge in Our Selections</h1>
      <div className='flex flex-wrap'>
        {data.map((category) => (
          <CategoryItem key={category.id} category={category} image={getImage(category.name)} />
        ))}
      </div>
    </div>
  );
};

export default Categories;
