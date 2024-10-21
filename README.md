## Project Definitions

1.  **Sax-Cod.java:**
A program that parses `EPAXML.xml` file using the SAX method and generates output in the exact format below for centers where their `LocationAddressStateCode` is WY:<br>
&nbsp;&nbsp; registryId of FacilitySite: LatitudeMeasure, LongitudeMeasure <br>
For example, the first line of the output would be as follows:<br>
&nbsp;&nbsp; 110022448289: 42.755711, -110.928876

3. **Dom-Code.java:**
A program that uses a DOM parser to generate a new version of the given XML file (`Example.xml`) such that:
&nbsp; - It only includes `FacilitySite` elements where the `LocationAddressStateCode` is WY.
&nbsp; - The `GeneralProfileElectronicAddress` tag and its children are removed.
&nbsp; - Since we are now only keeping centers related to WY, the `LocationAddressStateCode` tag is also removed.
&nbsp; - The `Program` tag and all its children, except `ElectronicAddressText`, are removed. Move the `ElectronicAddressText` tag outside of `Program` as a direct child of `FacilitySite` and wrap its content inside a new tag named `URL`. For example, the following XML code: <br>
        ```
        <FacilitySite> 
          ... 
          <Program> 
            ... 
            <ProgramProfileElectronicAddress> 
              <ElectronicAddressText>http://example.com</ElectronicAddressText> 
              <ElectronicAddressTypeName>URL</ElectronicAddressTypeName> 
            </ProgramProfileElectronicAddress> 
          </Program> 
        </FacilitySite>
        ```
  <br>should be transformed into:<br>
        ```
        <FacilitySite>
          ...
          <ElectronicAddressText>
            <URL>http://example.com</URL>
          </ElectronicAddressText>
        </FacilitySite>
        ```
<br>Note that some `FacilitySite` elements may not have the `Program` tag, which requires no action. However, some may have multiple `Program` tags, and all of them should be removed, while the values of `ElectronicAddressText` are placed in `URL` tags similar to the example below:<br>
      ```
      <FacilitySite>
        ...
        <ElectronicAddressText>
          <URL>http://example1.com</URL>
          <URL>http://example2.com</URL>
          <URL>http://example3.com</URL>
        </ElectronicAddressText>
      </FacilitySite>
      ```

3. The output files are stored in the outputs.rar file.



