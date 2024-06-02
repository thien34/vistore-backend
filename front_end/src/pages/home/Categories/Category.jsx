import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'

const Category = ({ icon, name, path }) => {
    return (
        <div className='col-span-2'>
            <Link to={path} className='bg-secondary flex flex-col items-center gap-y-2 px-14 py-6 rounded-xl'>
                {icon}
                <p>{name}</p>
            </Link>
        </div>
    )
}

export default Category

Category.propTypes = {
    icon: PropTypes.element,
    name: PropTypes.string,
    path: PropTypes.string
}
