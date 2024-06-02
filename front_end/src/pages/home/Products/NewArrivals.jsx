import Product from '@/components/Product'
import Columns from '@/layouts/components/columns'
import PropTypes from 'prop-types'
const NewArrivals = ({ products }) => {
    return (
        <Columns className='gap-y-9'>
            <>
                {products.map((product) => (
                    <div key={product.id} className='col-span-3'>
                        <Product bgColor='secondary' product={product} />
                    </div>
                ))}
            </>
        </Columns>
    )
}

export default NewArrivals

NewArrivals.propTypes = {
    products: PropTypes.array
}
