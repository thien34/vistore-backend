import { useState } from 'react'
import Tags from './Tags'
import NewArrivals from './NewArrivals'
const tags = [
    {
        id: 1,
        name: 'New Arrivals',
        path: 'new-arrivals'
    },
    {
        id: 2,
        name: 'Best Seller',
        path: 'best-seller'
    },
    {
        id: 3,
        name: 'Featured',
        path: 'featured'
    }
]

const itemProduct = {
    id: 1,
    name: 'Samsung Galaxy Watch6 Classic 47mm Black',
    image: '/Iphone 14 pro.png',
    price: 100,
    currency: 'USD',
    path: '/product/1',
    isLiked: false
}

const generateProduct = () => {
    const products = []
    for (let index = 0; index < 8; index++) {
        itemProduct.id += index
        itemProduct.price += index
        products.push({
            ...itemProduct
        })
    }
    return products
}
const data = generateProduct()
const Products = () => {
    const [activeTag, setActiveTag] = useState(1)
    const handleActiveTag = (id) => {
        setActiveTag(id)
        // handle api
    }
    return (
        <div className='max-w-screen-xl mx-auto px-4 py-14'>
            <Tags tags={tags} activeTag={activeTag} handleActiveTag={handleActiveTag} />
            <div className='mt-8'>
                <NewArrivals products={data} />
            </div>
        </div>
    )
}

export default Products
