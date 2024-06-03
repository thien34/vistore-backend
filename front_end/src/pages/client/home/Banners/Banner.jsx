import { Button } from '@/components/ui/button'
import clsx from 'clsx'
import PropTypes from 'prop-types'

const Banner = ({ bgColor = '#fff', product }) => {
    const bgs = {
        FFF: 'bg-white',
        F9F9F9: 'bg-[#F9F9F9]',
        EAEAEA: 'bg-secondary ',
        '2C2C2C': 'bg-[#2C2C2C] text-secondary'
    }
    return (
        <div className={clsx(bgs[bgColor])}>
            <div className='aspect-square p-8'>
                <img src={product.image} alt={product.name} className='size-full object-contain' />
            </div>
            <div className='px-8 *:mb-4'>
                <h3 className='text-3xl font-light'>{product.name}</h3>
                <p className='text-sm text-[#909090]'>{product.description}</p>
                <Button
                    className={clsx(
                        'bg-transparent shadow-none py-4 px-14 h-min',
                        'border border-primary text-primary hover:text-background',
                        {
                            ' border-secondary text-secondary hover:bg-transparent': bgColor === '2C2C2C'
                        }
                    )}
                >
                    Show now
                </Button>
            </div>
        </div>
    )
}

export default Banner

Banner.propTypes = {
    bgColor: PropTypes.string,
    product: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired
    })
}
