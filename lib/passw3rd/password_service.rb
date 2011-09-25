module Passw3rd
  class PasswordService
    VERSION = '0.1.0'

    def self.getPassword (password_file, key_path=nil)
      encoded_password = IO.readlines(password_file).join
      encrypted_password = Base64.decode64(encoded_password)
      PasswordService.decrypt(encrypted_password, key_path)
    end

    def self.write_password_file password, output_path, key_path
      enc_password = PasswordService.encrypt(password, key_path)
      base64pw = Base64.encode64(enc_password) 
      File.open(output_path, 'w') { |f| f.write base64pw }
    end

    def self.encrypt(password, key_path = nil)
      pair = KeyLoader.load(key_path)
      cipher = OpenSSL::Cipher::Cipher.new('aes-128-cbc')
      cipher.encrypt
      cipher.key = pair.key
      cipher.iv = pair.iv
      begin
        e = cipher.update(password)
        e << cipher.final
      rescue OpenSSL::Cipher::CipherError=>err
        puts "Couldn't encrypt password."
        raise err
      end

    end

    def self.decrypt(password, key_path = nil)
      pair = KeyLoader.load(key_path)
      cipher = OpenSSL::Cipher::Cipher.new('aes-128-cbc')
      cipher.decrypt
      cipher.key = pair.key
      cipher.iv = pair.iv
      begin
        d = cipher.update(password)
        d << cipher.final
      rescue OpenSSL::Cipher::CipherError => err
        puts "Coudln't decrypt password.  Are you using the right keys?"
        raise err
      end

    end
  end
end