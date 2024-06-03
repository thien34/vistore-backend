import { Heart } from 'lucide-react'
import { Button } from '@/components/ui/button'
import { useState } from 'react'
import clsx from 'clsx'
import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'
import { fromatCurrency } from '@/lib/helper'

const Product = ({ product, bgColor = 'main' }) => {
    const [heart, setHeart] = useState(product.isLiked)
    const handleHeart = () => setHeart(!heart)
    const price = fromatCurrency(product.price, product.currency)
    return (
        <div
            className={clsx('p-6 rounded-lg', {
                'bg-secondary': bgColor === 'secondary',
                'bg-[#F6F6F6]': bgColor === 'main'
            })}
        >
            <div
                onClick={handleHeart}
                className={clsx('flex justify-end cursor-pointer hover:opacity-100 transition-all', {
                    '*:text-red-500 opacity-100 *:fill-red-500': heart,
                    '*:text-primary opacity-45': !heart
                })}
            >
                <Heart strokeWidth={1.5} />
            </div>
            <Link to={product.path}>
                <div className='px-6 py-4 mt-4'>
                    <img src={product.image} alt='' className='size-full object-cover aspect-square' />
                </div>
            </Link>
            <div className='flex flex-col gap-y-3 text-center px-4'>
                <Link to={product.path}>
                    <h4 className='hover:underline'>{product.name}</h4>
                </Link>
                <p className='text-2xl font-semibold'>{price}</p>
                <div className='*:w-full px-2'>
                    <Button className='font-normal leading-6 h-fit py-3'>Buy now</Button>
                </div>
            </div>
        </div>
    )
}

export default Product

Product.propTypes = {
    product: PropTypes.shape({
        name: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired,
        path: PropTypes.string.isRequired,
        isLiked: PropTypes.bool,
        price: PropTypes.number,
        currency: PropTypes.string
    }),
    bgColor: PropTypes.string
}
