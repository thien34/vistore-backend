import { Button } from '@/components/ui/button'
import clsx from 'clsx'
import PropTypes from 'prop-types'
const ProductBig = ({ product, bgColor = 'white' }) => {
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
            <div className={clsx('ml-14 pr-8 flex flex-col justify-center items-start flex-1')}>
                <h4 className='font-thin text-6xl'>
                    {name.firtTitle}
                    <span className='font-medium pl-3'>{name.lastTitle}</span>
                </h4>
                <p className='text-[#909090] text-sm mt-2'>{product.description}</p>
                <Button className='bg-transparent mt-4 shadow-none border border-primary text-primary hover:text-background py-4 px-14 h-min'>
                    Show now
                </Button>
            </div>
            <div className={clsx('w-2/5 shrink-0')}>
                <img className={clsx('h-full object-cover object-left')} src={product.image} alt={product.name} />
            </div>
        </div>
    )
}

export default ProductBig

ProductBig.propTypes = {
    product: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired
    }),
    bgColor: PropTypes.string,
    positionImage: PropTypes.shape('left' | 'right'),
    textSize: PropTypes.string
}
