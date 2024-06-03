import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'
const Logo = ({ src, alt = 'VStore' }) => {
    return (
        <div>
            <Link to={'/'}>
                <h1 className='font-SF-Pro text-2xl font-bold'>
                    <img src={src} alt={alt} />
                </h1>
            </Link>
        </div>
    )
}

Logo.propTypes = {
    src: PropTypes.string.isRequired,
    alt: PropTypes.string
}

export default Logo
