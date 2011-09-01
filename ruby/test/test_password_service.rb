require 'rubygems'
require 'test/unit'
require 'tmpdir'
require 'SecureRandom'
require File.expand_path('../../lib/passw3rd.rb',  __FILE__)

class PasswordServiceTest < Test::Unit::TestCase
  def setup
    random_num = SecureRandom.random_number(5000)
    @random_string = SecureRandom.random_bytes(5000 + random_num)
  end
  
  def test_enc_dec
    enc = ::Passw3rd::PasswordService.encrypt(@random_string)
    dec = ::Passw3rd::PasswordService.decrypt(enc)
    
    assert_equal(@random_string, dec)
  end
  
  def test_gen_key
    ::Passw3rd::KeyLoader.create_key_iv_file(Dir.tmpdir)
    
    enc = ::Passw3rd::PasswordService.encrypt(@random_string, Dir.tmpdir)
    dec = ::Passw3rd::PasswordService.decrypt(enc, Dir.tmpdir)
    
    assert_equal(@random_string, dec)
  end
end