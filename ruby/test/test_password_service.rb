require 'rubygems'
require 'password_service'
require 'test/unit'
require 'tmpdir'
require 'active_support/secure_random'


class PasswordServiceTest < Test::Unit::TestCase
  def setup
    random_num = ActiveSupport::SecureRandom.random_number(5000)
    @random_string = SecureRandom.random_bytes(5000 + random_num)
  end
  
  def test_enc_dec
    enc = PasswordService.encrypt(@random_string)
    dec = PasswordService.decrypt(enc)
    
    assert_equal(@random_string, dec)
  end
  
  def test_gen_key
    KeyLoader.create_key_iv_file(Dir.tmpdir)
    
    enc = PasswordService.encrypt(@random_string, Dir.tmpdir)
    dec = PasswordService.decrypt(enc, Dir.tmpdir)
    
    assert_equal(@random_string, dec)
  end
end