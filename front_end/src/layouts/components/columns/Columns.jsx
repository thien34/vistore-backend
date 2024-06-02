import clsx from 'clsx'
import PropTypes from 'prop-types'
const Columns = ({ children, className }) => {
    return (
        <div className='max-w-screen-2xl mx-auto'>
            <div className={clsx('h-full justify-center grid grid-cols-12 gap-x-8', className)}>{children}</div>
        </div>
    )
}

Columns.propTypes = {
    children: PropTypes.element,
    className: PropTypes.string
}
export default Columns
