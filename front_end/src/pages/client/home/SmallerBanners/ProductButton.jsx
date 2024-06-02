import clsx from 'clsx'
import PropTypes from 'prop-types'
const ProductButton = ({ product, bgColor = 'white', textColor = 'black' }) => {
    function capitalizeFirstLetter(string) {
        const title = string.split(' ')
        const lastTitle = title.pop()
        const firtTitle = title.join(' ')
        return {
            firtTitle: firtTitle,
            lastTitle: lastTitle
        }
    }
    const name = capitalizeFirstLetter(product.name)
    return (
        <div style={{ backgroundColor: bgColor }} className='col-span-6 flex'>
            <div className={clsx('w-1/3 shrink-0')}>
                <img className={clsx('h-full object-cover object-right')} src={product.image} alt={product.name} />
            </div>
            <div className={clsx('ml-10 pr-8 flex flex-col justify-center flex-1')}>
                <h4 className='font-light text-3xl' style={{ color: textColor }}>
                    {name.firtTitle}
                    <span className='font-medium pl-3'>{name.lastTitle}</span>
                </h4>
                <p className='text-[#909090] text-sm mt-3'>{product.description}</p>
            </div>
        </div>
    )
}

export default ProductButton

ProductButton.propTypes = {
    product: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired
    }),
    bgColor: PropTypes.string,
    textColor: PropTypes.string,
    positionImage: PropTypes.shape('left' | 'right')
}
