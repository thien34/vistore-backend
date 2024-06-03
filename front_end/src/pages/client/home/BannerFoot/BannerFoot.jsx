import PropTypes from 'prop-types'
const BannerFoot = ({ bg }) => {
    return (
        <div
            className='max-w-screen-2xl mx-auto h-[465px] bg-no-repeat bg-cover bg-center'
            style={{ backgroundImage: `url(${bg})` }}
        ></div>
    )
}

export default BannerFoot

BannerFoot.propTypes = {
    bg: PropTypes.string
}
