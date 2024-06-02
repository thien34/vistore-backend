import Product from '@/components/client/Product'
import Columns from '@/layouts/client/components/columns'
import PropTypes from 'prop-types'
const Productss = ({ products, bgColor, itemOnRow = 4 }) => {
    const col = 12 / Number(itemOnRow)
    return (
        <Columns className='gap-y-9'>
            <>
                {products.map((product) => (
                    <div key={product.id} className={`col-span-${col}`}>
                        <Product bgColor={bgColor} product={product} />
                    </div>
                ))}
            </>
        </Columns>
    )
}

export default Productss

Productss.propTypes = {
    products: PropTypes.array,
    bgColor: PropTypes.string,
    itemOnRow: PropTypes.number
}
