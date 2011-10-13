require 'rubygems'
require 'test/unit'
require 'tmpdir'

require File.expand_path('../../lib/passw3rd.rb',  __FILE__)

class PasswordServiceTest < Test::Unit::TestCase
  def setup
    random_num = sudorandumb
    @random_string = sudorandumb

    ::Passw3rd::KeyLoader.create_key_iv_file(Dir.tmpdir)
    ::Passw3rd::PasswordService.key_file_dir = Dir.tmpdir    
  end
  
  def test_enc_dec
    enc = ::Passw3rd::PasswordService.encrypt(@random_string)
    dec = ::Passw3rd::PasswordService.decrypt(enc)
    
    assert_equal(@random_string, dec)
  end

  def test_enc_raise_on_blank_password
    assert_raise(ArgumentError) { ::Passw3rd::PasswordService.encrypt("") }
  end

  def test_enc_raise_on_nil_password
    assert_raise(ArgumentError) { ::Passw3rd::PasswordService.encrypt(nil) }
  end

  def test_set_and_get_password
    password_file = ::Passw3rd::PasswordService.write_password_file(@random_string, "test")
    decrypted = Passw3rd::PasswordService.get_password("test")
    assert_equal(@random_string, decrypted)
  end

  def test_set_and_get_password_custom_dir
    ::Passw3rd::PasswordService.password_file_dir = Dir.tmpdir    

    password_file = ::Passw3rd::PasswordService.write_password_file(@random_string, "test2")
    decrypted = Passw3rd::PasswordService.get_password("test2")
    assert_equal(@random_string, decrypted)
  end  
  
  def test_gen_key
    enc = ::Passw3rd::PasswordService.encrypt(@random_string)
    dec = ::Passw3rd::PasswordService.decrypt(enc)
    
    assert_equal(@random_string, dec)
  end
  
  def sudorandumb
    Digest::SHA1.hexdigest(Time.now.to_s.split(//).sort_by{rand}.join)
  end
end
